<script>
	import Chart from 'chart.js/auto';
	import { onMount } from 'svelte';
	import {monthIncome, monthExpense, isCategorizedExpense, categorizedExpense, expenseCategoryLabel} from "../../../stores.js";
	import { globalStyles } from "../../../styles.js";

	let ctx;
	let chartCanvas;
	let chart = null;

	let generatedData;

	function createGraph() {
		try {
			if (chartCanvas.getContext('2d') !== undefined) {
				ctx = chartCanvas.getContext('2d');
				if (!chart) {
					console.log(generatedData);
					chart = new Chart(ctx, {
						type: 'line',
						data: generatedData,
						options: {
							scales: {
								y: {
									ticks: {
										color: 'rgb(255,255,255)'
									}
								},
								x: {
									ticks: {
										color: 'rgb(255,255,255)'
									}
								}
							},
							responsive: true,
							maintainAspectRatio: false
						}
					});
				} else {
					if ($isCategorizedExpense === true) {
						chart.data.labels = generatedData.labels;
						chart.data.datasets = generatedData.datasets;
					} else {
						generatedData.datasets = generatedData.datasets.filter(dataset => dataset.label !== $expenseCategoryLabel);
						chart.data.labels = generatedData.labels;
						chart.data.datasets = generatedData.datasets;
					}
					chart.update();
				}
			}
		} catch (error) {
			console.error('Error:', error);
		}
	}

	$: {
		if (isCategorizedExpense) {
			const allDates = [...new Set([...$monthExpense, ...$categorizedExpense].map(item => item.date))];
			const uniqueDates = allDates.sort((a, b) => new Date(a) - new Date(b));

			const categorizedValues = uniqueDates.map(date => $categorizedExpense.filter(item => item.date === date).reduce((total, item) => total + item.amount, 0));
			const expenseValues = uniqueDates.map(date => $monthExpense.filter(item => item.date === date).reduce((total, item) => total + item.amount, 0));

			generatedData = {
				labels: uniqueDates,
				datasets: [
					{
						label: $expenseCategoryLabel,
						backgroundColor: "rgba(21, 194, 58, 0.4)",
						borderColor: "rgba(21, 194, 58, 1)",
						data: categorizedValues,
						tension: 0.2,
						fill: true
					},
					{
						label: "Expense",
						backgroundColor: "rgba(194, 21, 96, 0.4)",
						borderColor: "rgba(194, 21, 96, 1)",
						data: expenseValues,
						tension: 0.4,
						fill: true
					}
				]
			};

			const tempData = generatedData.datasets.filter(dataset => dataset.label !== undefined);

			generatedData = {
				labels: generatedData.labels || [],
				datasets: tempData
			};
		} else {
			const allDates = [...new Set([...$monthExpense].map(item => item.date))];
			const uniqueDates = allDates.sort((a, b) => new Date(a) - new Date(b));

			const expenseValues = uniqueDates.map(date => $monthExpense.filter(item => item.date === date).reduce((total, item) => total + item.amount, 0));

			generatedData = {
				labels: uniqueDates,
				datasets:
						{
							label: "Expense",
							backgroundColor: "rgba(194, 21, 96, 0.4)",
							borderColor: "rgba(194, 21, 96, 1)",
							data: expenseValues,
							tension: 0.4,
							fill: true
						}
			};

			const tempData = generatedData.datasets.filter(dataset => dataset.label !== undefined);

			generatedData = {
				labels: generatedData.labels || [],
				datasets: tempData
			};
		}

		if ($monthIncome || $monthExpense || $isCategorizedExpense || $categorizedExpense) {
			createGraph();
		}
	}

	onMount(() => {
		createGraph();
	});
</script>

<div id="chart" style="background-color: {$globalStyles.mainColor}">
	<canvas id="canvas" bind:this={chartCanvas}></canvas>
</div>

<style>
	#chart {
		min-width: 0;
		min-height:0;
		box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
		transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
		display: flex;
		flex: 1 1 auto;
		flex-grow: 2;
		border-radius: 10px;
		margin: 0 10px 10px;
		padding: 10px;
	}

	#chart:hover {
		box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
	}

	@media only screen and (max-width: 900px) {
		#chart {
			min-height: 400px;
		}
	}
</style>
