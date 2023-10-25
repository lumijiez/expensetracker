<script>
	import Chart from 'chart.js/auto';
	import { onMount } from 'svelte';
	import axios from 'axios';
	import { getCookie } from "svelte-cookie";

	let ctx;
	let chartCanvas;

	onMount(async () => {
		const token = getCookie('access_token');

		const config = {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		};

		try {
			const response = await axios.get('http://localhost:8081/incomes/personal-incomes', config);
			const incomeData = response.data;

			// Create a function to group and sum incomes by category
			function groupAndSumByCategory(incomes) {
				const groupedData = new Map();
				incomes.forEach(income => {
					const category = income.incomeCategory.name;
					if (groupedData.has(category)) {
						groupedData.set(category, groupedData.get(category) + income.amount);
					} else {
						groupedData.set(category, income.amount);
					}
				});
				return groupedData;
			}

			// Group and sum incomes by category
			const groupedIncomeData = groupAndSumByCategory(incomeData);

			// Extract category names and summed values
			const chartLabels = Array.from(groupedIncomeData.keys());
			const chartValues = Array.from(groupedIncomeData.values());

			ctx = chartCanvas.getContext('2d');
			new Chart(ctx, {
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
		} catch (error) {
			console.error('Error:', error);
		}
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
