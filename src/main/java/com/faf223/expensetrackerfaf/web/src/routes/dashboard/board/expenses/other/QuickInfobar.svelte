<script>
    import { onMount } from 'svelte';
    import { incomeData, expenseData } from "../../../stores.js";
    import {globalStyles} from "../../../styles.js";

    let infobar1, infobar2, infobar3, infobar4;
    let totalExpenses = 0;
    let totalIncomes = 0;
    let lastMonthIncome = 800; // Dummy last month's income
    let lastMonthExpense = 200; // Dummy last month's expense

    function updateInfo() {
        totalExpenses = $expenseData.reduce((total, item) => total + parseInt(item.amount), 0);
        totalIncomes = $incomeData.reduce((total, item) => total + parseInt(item.amount), 0);

        const incomeDifference = ((totalIncomes - lastMonthIncome) / lastMonthIncome) * 100;
        const expenseDifference = ((lastMonthExpense - totalExpenses) / lastMonthExpense) * 100;

        try {
            infobar1.innerHTML = `<span style="font-size: larger">Total expenses:</span><br><span style="color:red;font-size: xxx-large">${totalExpenses.toFixed(2)}$</span>`;
            infobar2.innerHTML = `<span style="font-size: larger">Total incomes:</span><br><span style="color:green;font-size: xxx-large">${totalIncomes.toFixed(2)}$</span>`;

            infobar3.innerHTML = `<span style="font-size: larger">Income by last month:</span><br><span style="color:blue;font-size: xxx-large">${incomeDifference.toFixed(2)}%</span>`;
            infobar4.innerHTML = `<span style="font-size: larger">Expense by last month:</span><br><span style="color:orange;font-size: xxx-large">${expenseDifference.toFixed(2)}%</span>`;
        } catch {
            console.log("not yet loaded");
        }
    }

    $: {
        if ($incomeData || $expenseData) {
            updateInfo();
        }
    }

    onMount(() => {
        updateInfo();
    });
</script>

<div id="quickInfobar">
    <div class="infobarElement" bind:this={infobar1} style="background-color: {$globalStyles.mainColor}"></div>
    <div class="infobarElement" bind:this={infobar2} style="background-color: {$globalStyles.mainColor}"></div>
    <div class="infobarElement" bind:this={infobar3} style="background-color: {$globalStyles.mainColor}"></div>
    <div class="infobarElement" bind:this={infobar4} style="background-color: {$globalStyles.mainColor}"></div>
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
        color: white;
        padding: 10px;
        border-radius: 10px;
        background-color: #212942;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
        overflow: hidden;
    }

    .infobarElement:hover {
        box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    }
</style>
