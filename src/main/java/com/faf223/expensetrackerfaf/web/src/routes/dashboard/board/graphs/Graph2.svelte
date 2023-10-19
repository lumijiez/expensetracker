<script>
	import chartjs from 'chart.js/auto';
	import { onMount } from 'svelte';

    var MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        var config = {
            type: 'line',
            data: {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [{
                    fill: false,
                    data: [
                        5,
                        6,
                        3,
                        4
                    ]
                }, {
                    label: "My Second dataset ",
                    fill: false,
                    data: [
                        5,
                        6,
                        3,
                        4
                    ]
                }]
            },
            options: {
                title:{
                    display:true,
                    text: "Chart.js Line Chart - Animation Progress Bar"
                },
                maintainAspectRatio: false
            }
        };

	let chartValues = [20, 10, 5, 2, 20, 30, 45];
	let chartLabels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
	let ctx;
	let chartCanvas;

	onMount(async (promise) => {
		  ctx = chartCanvas.getContext('2d');
			var chart = new chartjs(ctx, config);
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
		margin:10px;
        background-color: #ffdde2;
    }

    #chart:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }
</style>