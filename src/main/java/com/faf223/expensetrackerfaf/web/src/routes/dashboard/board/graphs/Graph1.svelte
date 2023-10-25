<script>
	import Chart from 'chart.js/auto';
	import { onMount } from 'svelte';
	import { incomeData } from "../../stores.js";

	let ctx;
	let chartCanvas;
	let chart = null;

	function createGraph(data) {
		try {
			function groupAndSumByCategory(incomes) {
				const groupedData = new Map();
				incomes.forEach(income => {
							const category = income.incomeCategory.name;
							if (groupedData.has(category)) {
								groupedData.set(category, groupedData.get(category) + parseInt(income.amount));
							} else {
								groupedData.set(category, income.amount);
							}
						}
				);
				return groupedData;
			}

			const groupedIncomeData = groupAndSumByCategory(data);

			const chartLabels = Array.from(groupedIncomeData.keys());
			const chartValues = Array.from(groupedIncomeData.values());

			ctx = chartCanvas.getContext('2d');

			if (!chart) {
				chart = new Chart(ctx, {
					type: 'bar',
					data: {
						labels: chartLabels,
						datasets: [{
							label: 'Revenue',
							backgroundColor: 'rgb(255, 99, 132)',
							data: chartValues
						}]
					},
					options: {
						responsive: true,
						maintainAspectRatio: false
					}
				});
			} else {
				chart.data.labels = chartLabels;
				chart.data.datasets[0].data = chartValues;
				console.log(chart.data.datasets[0].data);
				chart.update();
			}
		} catch (error) {
			console.error('Error:', error);
		}
	}

	$: {
		if ($incomeData) {
			createGraph($incomeData);
			console.log($incomeData);
		}
	}

	onMount(() => {
		createGraph($incomeData);
	});
</script>

<div id="chart">
	<canvas bind:this={chartCanvas}></canvas>
</div>

<style>
	#chart {
		box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
		transition: all 0.3s cubic-bezier(.25,.8,.25,1);
		flex: 1;
		border-radius: 10px;
		margin: 10px;
		background-color: #d3d3d3;
	}

	#chart:hover {
		box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	}
</style>
