<script>
    import { onMount } from 'svelte';
    import { incomeData, expenseData, currencyLabel } from "../../../stores.js";
    import {globalStyles} from "../../../styles.js";

    let infobar1, infobar2, infobar3, infobar4;
    let totalExpenses = 0;
    let totalIncomes = 0;
    let lastMonthIncome = 800;
    let lastMonthExpense = 200;
    let incomeDifference;
    let expenseDifference;

    function updateInfo() {
        totalExpenses = $expenseData.reduce((total, item) => total + parseInt(item.amount), 0);
        totalIncomes = $incomeData.reduce((total, item) => total + parseInt(item.amount), 0);

        incomeDifference = ((totalIncomes - lastMonthIncome) / lastMonthIncome) * 100;
        expenseDifference = ((lastMonthExpense - totalExpenses) / lastMonthExpense) * 100;
    }

    $: {
        if ($currencyLabel) {
            if ($currencyLabel === 'USD') {
                lastMonthIncome = 800;
                lastMonthExpense = 200;
            }

            if ($currencyLabel === 'MDL') {
                lastMonthIncome = 800 / 0.056;
                lastMonthExpense = 200 / 0.056;
            }

            if ($currencyLabel === 'EUR') {
                lastMonthIncome = 800 / 1.08;
                lastMonthExpense = 200 / 1.08;
            }

            if ($currencyLabel === 'GBP') {
                lastMonthIncome = 800 / 1.26;
                lastMonthExpense = 200 / 1.26;
            }
        }

        if ($incomeData || $expenseData) {
            updateInfo();
        }


    }

    onMount(() => {
        updateInfo();
    });
</script>

<div id="quickInfobar">
    <div class="firstTwo">
        <div class="infobarElement" bind:this={infobar1} style="background-color: {$globalStyles.mainColor}">
            <span class="infobarSpan">Total expenses:</span><br><span class="dataSpan" style="color:#ab1a3c">{totalExpenses.toFixed(2)} {$currencyLabel}</span>
        </div>
        <div class="infobarElement" bind:this={infobar2} style="background-color: {$globalStyles.mainColor}">
            <span class="infobarSpan">Total incomes:</span><br><span class="dataSpan" style="color:#38cc1b">{totalIncomes.toFixed(2)} {$currencyLabel}</span>
        </div>
    </div>

    <div class="secondTwo">
        <div class="infobarElement" bind:this={infobar3} style="background-color: {$globalStyles.mainColor}">
            <span class="infobarSpan">Income by last month:</span><br><span class="dataSpan" style="color:#2763db">{incomeDifference.toFixed(2)}%</span>
        </div>
        <div class="infobarElement" bind:this={infobar4} style="background-color: {$globalStyles.mainColor}">
            <span class="infobarSpan">Expense by last month:</span><br><span class="dataSpan" style="color:#dba527">{expenseDifference.toFixed(2)}%</span>
        </div>
    </div>

</div>

<style>

    .firstTwo {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }

    .secondTwo {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }

    .infobarSpan {
        font-size: larger;
        color: #00FEFC
    }

    .dataSpan {
        color:#ab1a3c;
        font-size: xxx-large;
    }

    #quickInfobar {
        /*display: flex;*/
        /*justify-content: space-between;*/
        /*flex-wrap: wrap;*/
        min-height: 0;
        flex: 1 1 auto;
        margin: 20px;
    }

    @media only screen and (min-width: 768px) and (max-width: 1200px) {
        .infobarSpan {
            font-size: medium;
        }

        .dataSpan {
            font-size: large;
        }
    }

    .infobarElement {
        font-family: Inconsolata,"Source Sans Pro",sans-serif;
        font-size: larger;
        margin: 10px;
        min-width: 0;
        min-height: 0;
        flex: 1 1 auto;
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
