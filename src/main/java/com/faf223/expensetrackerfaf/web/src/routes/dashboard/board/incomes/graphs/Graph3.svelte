<script>
	import Chart from 'chart.js/auto';
	import { onMount } from 'svelte';
	import {monthIncome, monthExpense, isCategorizedIncome, categorizedIncome, incomeCategoryLabel} from "../../../stores.js";
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
					if ($isCategorizedIncome === true) {
						chart.data.labels = generatedData.labels;
						chart.data.datasets = generatedData.datasets;
					} else {
						generatedData.datasets = generatedData.datasets.filter(dataset => dataset.label !== $incomeCategoryLabel);
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
		if (isCategorizedIncome) {
			const allDates = [...new Set([...$monthIncome, ...$categorizedIncome].map(item => item.date))];
			const uniqueDates = allDates.sort((a, b) => new Date(a) - new Date(b));

			const categorizedValues = uniqueDates.map(date => $categorizedIncome.filter(item => item.date === date).reduce((total, item) => total + item.amount, 0));
			const incomeValues = uniqueDates.map(date => $monthIncome.filter(item => item.date === date).reduce((total, item) => total + item.amount, 0));

			generatedData = {
				labels: uniqueDates,
				datasets: [
					{
						label: $incomeCategoryLabel,
						backgroundColor: "rgba(21, 194, 58, 0.4)",
						borderColor: "rgba(21, 194, 58, 1)",
						data: categorizedValues,
						tension: 0.2,
						fill: true
					},
					{
						label: "Revenue",
						backgroundColor: "rgba(194, 21, 96, 0.4)",
						borderColor: "rgba(194, 21, 96, 1)",
						data: incomeValues,
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
			const allDates = [...new Set([...$monthIncome].map(item => item.date))];
			const uniqueDates = allDates.sort((a, b) => new Date(a) - new Date(b));

			const incomeValues = uniqueDates.map(date => $monthIncome.filter(item => item.date === date).reduce((total, item) => total + item.amount, 0));

			generatedData = {
				labels: uniqueDates,
				datasets:
						{
							label: "Income",
							backgroundColor: "rgba(194, 21, 96, 0.4)",
							borderColor: "rgba(194, 21, 96, 1)",
							data: incomeValues,
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

		if ($monthIncome || $monthExpense || $isCategorizedIncome || $categorizedIncome) {
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
