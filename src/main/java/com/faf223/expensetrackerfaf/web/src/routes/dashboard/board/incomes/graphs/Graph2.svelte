<script>
	import Chart from 'chart.js/auto';
	import {onMount} from 'svelte';
	import {monthIncome} from "../../../stores.js";
	import {globalStyles} from "../../../styles.js";

	let ctx;
	let chartCanvas;
	let chart = null;

	function groupAndSumByCategory() {
		const groupedData = new Map();
		$monthIncome.forEach(income => {
					const category = income.incomeCategory.name;
					if (groupedData.has(category)) {
						groupedData.set(category, groupedData.get(category) + parseInt(income.amount));
					} else {
						groupedData.set(category, parseInt(income.amount));
					}
				}
		);

		return new Map([...groupedData.entries()].sort());
	}

	function createGraph() {
		try {
			const groupedIncomeData = groupAndSumByCategory();

			const chartLabels = [];
			const chartValues = [];

			for (const [label, value] of groupedIncomeData.entries()) {
				chartLabels.push(label);
				chartValues.push(value);
			}

			ctx = chartCanvas.getContext('2d');

			if (!chart) {
				chart = new Chart(ctx, {
					type: 'doughnut',
					data: {
						labels: chartLabels,
						datasets: [{
							label: 'Incomes',
							data: chartValues
						}]
					},
					options: {
						responsive: true,
						maintainAspectRatio: false,
						backgroundColor: [
							'rgb(255, 140, 140)',
							'rgb(140, 180, 255)',
							'rgb(255, 200, 140)',
							'rgb(160, 200, 160)',
							'rgb(160, 130, 200)',
							'rgb(255, 160, 140)',
							'rgb(140, 180, 255)',
							'rgb(160, 255, 160)',
							'rgb(255, 140, 120)',
							'rgb(160, 140, 200)',
							'rgb(255, 220, 140)',
							'rgb(140, 255, 255)',
							'rgb(255, 160, 140)',
							'rgb(160, 255, 160)',
							'rgb(160, 160, 255)'
						],
						plugins: {
							legend: {
								position: 'bottom',
								align: 'start',
								fullWidth: false,
								labels: {
									font: {
										weight: 'bold'
									},
									color: '#fff'
								}
							}
						}
					}
				});
			} else {
				chart.data.labels = chartLabels;
				chart.data.datasets[0].data = chartValues;
				chart.update();
			}
		} catch (error) {
			console.error('Error:', error);
		}
	}

	$: {
		if ($monthIncome) {
			createGraph();
		}
	}

	onMount(() => {
		createGraph();
	});
</script>

<div id="chart" style="background-color: {$globalStyles.mainColor}">
	<canvas bind:this={chartCanvas}></canvas>
</div>

<style>
	#chart {
		min-width: 0;
		min-height:0;
		box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
		transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
		display: flex;
		flex: 1 1 auto;
		flex-grow: 1;
		padding: 10px;
		border-radius: 10px;
		margin: 0 10px 10px;
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
