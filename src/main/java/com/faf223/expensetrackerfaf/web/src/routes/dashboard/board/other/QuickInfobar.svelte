<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";

    let infobar1, infobar2, infobar3, infobar4;
    let totalExpenses = 0;
    let totalIncomes = 0;

    onMount(async () => {
        const token = getCookie('access_token');

        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        try {
            const [incomesResponse, expensesResponse] = await Promise.all([
                axios.get('http://localhost:8081/incomes/personal-incomes', config),
                axios.get('http://localhost:8081/expenses/personal-expenses', config)
            ]);

            const incomesData = incomesResponse.data;
            const expensesData = expensesResponse.data;

            totalExpenses = expensesData.reduce((total, item) => total + item.amount, 0);
            totalIncomes = incomesData.reduce((total, item) => total + item.amount, 0);

            infobar1.innerHTML = `<span style="font-size: larger">Total expenses:</span><br><span style="color:red;font-size: xxx-large">${totalExpenses}$`;
            infobar2.innerHTML = `<span style="font-size: larger">Total incomes:</span><br><span style="color:green;font-size: xxx-large">${totalIncomes}$</span>`;
        } catch (error) {
            console.error('Error:', error);
        }
    });
</script>

<div id="quickInfobar">
    <div class="infobarElement" bind:this={infobar1}></div>
    <div class="infobarElement" bind:this={infobar2}></div>
    <div class="infobarElement" bind:this={infobar3}></div>
    <div class="infobarElement" bind:this={infobar4}></div>
</div>

<style>
    #quickInfobar {
        display: flex;
        justify-content: space-between;
        margin: 20px;
    }

    .infobarElement {
        margin: 10px;
        width: 200px;
        min-width: 100px;
        height: 100px;
        color:white;
        padding: 10px;
        border-radius: 10px;
        background-color: #212942;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
    }

    .infobarElement:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }
</style>