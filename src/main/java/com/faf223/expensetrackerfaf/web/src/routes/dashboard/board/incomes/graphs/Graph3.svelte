<script>
	import Chart from 'chart.js/auto';
	import { onMount } from 'svelte';
	import { incomeData, expenseData } from "../../../stores.js";
	import {globalStyles} from "../../../styles.js";

	let componentStyles;

	$: {
		console.log("got here")
		componentStyles = $globalStyles;
	}

	let ctx;
	let chartCanvas;
	let chart = null;

	function createGraph() {
		try {
			const totalIncomes = $incomeData.reduce((total, item) => total + item.amount, 0);
			const totalExpenses = $expenseData.reduce((total, item) => total + item.amount, 0);

			const chartLabels = ['Incomes', 'Expenses'];
			const chartValues = [totalIncomes, totalExpenses];

			if (chartCanvas.getContext('2d') !== undefined) {
				ctx = chartCanvas.getContext('2d');
				if (!chart) {
					chart = new Chart(ctx, {
						type: 'pie',
						data: {
							labels: chartLabels,
							datasets: [{
								data: chartValues,
								backgroundColor: [
									'rgb(243, 188, 0)',
									'rgb(0, 117, 164)'
								],
							}]
						},
						options: {
							responsive: true,
							maintainAspectRatio: false,
							plugins: {
								legend: {
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
					const totalIncomesUpd = $incomeData.reduce((total, item) => total + parseInt(item.amount), 0);
					const totalExpensesUpd = $expenseData.reduce((total, item) => total + parseInt(item.amount), 0);

					const chartLabels = ['Incomes', 'Expenses'];
					const chartValues = [totalIncomesUpd, totalExpensesUpd];
					chart.data.labels = chartLabels;
					chart.data.datasets[0].data = chartValues;
					chart.update();
				}
			}
		} catch (error) {
			console.error('Error:', error);
		}
	}

	$: {
		if ($incomeData || $expenseData) {
			createGraph();
		}
	}

	onMount(() => {
		createGraph();
	});
</script>

<div id="chart" style="background-color: {componentStyles.mainColor}">
	<canvas bind:this={chartCanvas}></canvas>
</div>

<style>
	#chart {
		box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
		transition: all 0.3s cubic-bezier(.25,.8,.25,1);
		flex: 1;
		border-radius: 0 0 10px 10px;
		margin: 0 0 10px 10px;
		min-width: 0;
		min-height:0;
	}

	#chart:hover {
		box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	}
</style>
