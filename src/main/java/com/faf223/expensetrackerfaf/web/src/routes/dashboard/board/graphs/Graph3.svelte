<script>
	import chartjs from 'chart.js/auto';
	import { onMount } from 'svelte';
	import axios from 'axios';

	let ctx;
	let chartCanvas;

	onMount(async () => {
		const config = {
			headers: {
				'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW4uYmFsYW5AZ21haWwuY29tIiwiaWF0IjoxNjk3NzQ0MjY3LCJleHAiOjE2OTc4MzA2Njd9.hzbEDDuOVCY_EQAA8xGlJskQ2FQjw8o0CtFKB1dKYOU`
			}
		};

		try {
			const response = await axios.get('http://localhost:8081/incomes/00112233-4455-6677-8899-aabbccddeeaa', config);
			const incomeData = response.data; // Assuming the response is an array of income data

			// Extract income categories and their values
			const chartLabels = incomeData.map(item => item.category.categoryName);
			const chartValues = incomeData.map(item => item.amount);

			ctx = chartCanvas.getContext('2d');
			new chartjs(ctx, {
				type: 'pie', // Set chart type to 'pie' for a pie chart
				data: {
					labels: chartLabels,
					datasets: [{
						data: chartValues,
						backgroundColor: ['red', 'orange', 'yellow', 'green', 'blue'], // Customize colors as needed
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
		background-color: #ffdde2;
	}

	#chart:hover {
		box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	}
</style>
