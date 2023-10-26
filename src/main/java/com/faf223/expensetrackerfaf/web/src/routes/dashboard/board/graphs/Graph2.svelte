<script>
	import Chart from 'chart.js/auto';
	import { onMount } from 'svelte';
	import { expenseData } from "../../stores.js";

	let ctx;
	let chartCanvas;
	let chart = null;

	function groupAndSumByCategory() {
		const groupedData = new Map();
		console.log($expenseData)
		$expenseData.forEach(expense => {
					const category = expense.expenseCategory.name;
					if (groupedData.has(category)) {
						groupedData.set(category, groupedData.get(category) + parseInt(expense.amount));
					} else {
						groupedData.set(category, expense.amount);
					}
				}
		);
		return groupedData;
	}

	function createGraph() {
		try {
			const groupedExpenseData = groupAndSumByCategory();

			const chartLabels = Array.from(groupedExpenseData.keys());
			const chartValues = Array.from(groupedExpenseData.values());

			ctx = chartCanvas.getContext('2d');

			if (!chart) {
				chart = new Chart(ctx, {
					type: 'bar',
					data: {
						labels: chartLabels,
						datasets: [{
							label: 'Spendings',
							backgroundColor: [
								'rgb(107, 80, 107)',
								'rgb(171, 61, 169)',
								'rgb(222, 37, 218)',
								'rgb(235, 68, 232)',
								'rgb(255, 128, 255)'],
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
				chart.update();
			}
		} catch (error) {
			console.error('Error:', error);
		}
	}

	$: {
		if ($expenseData) {
			createGraph();
		}
	}

	onMount(() => {
		createGraph();
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
