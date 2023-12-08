<script>
    import Graph1 from '../graphs/Graph1.svelte';
    import Graph3 from '../graphs/Graph3.svelte';
    import Incomes from "../infolists/Incomes.svelte";
    import {globalStyles} from "../../../styles.js";
    import { slide } from 'svelte/transition'
    import {dateText, expenseData, incomeData, incomeTypes, tempIncome, tempExpense} from "../../../stores.js";
    import axios from "axios";
    import {getCookie} from "svelte-cookie";
    import {onMount} from "svelte";

    let isDateDropdownExpanded = false
    let isCategoryDropdownExpanded = false
    let incomeAnalysisText = "REVENUE ANALYSIS: ";
    $ : {
        incomeAnalysisText = "REVENUE ANALYSIS: " + $dateText;
    }

    function clickHandlerDate() {
        isDateDropdownExpanded = !isDateDropdownExpanded
    }

    function clickHandlerCategory() {
        isCategoryDropdownExpanded = !isCategoryDropdownExpanded;
    }

    function clickOutsideHandler(event) {
        const isDateButton = event.target.closest("#btn1");
        const isCategoryButton = event.target.closest("#btn2");

        if (!isDateButton) {
            isDateDropdownExpanded = false;
        }

        if (!isCategoryButton) {
            isCategoryDropdownExpanded = false;
        }
    }

    onMount(() => {
        document.body.addEventListener("click", clickOutsideHandler);

        // Clean up the event listener when the component is destroyed
        return () => {
            document.body.removeEventListener("click", clickOutsideHandler);
        };
    });

    async function getToday() {
        var currentDate = new Date();
        var currentDay = currentDate.toISOString().split('T')[0];
        try {
            const response1 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?date=' + currentDay, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            incomeData.set(response1.data);
            tempIncome.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?date=' + currentDay, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            expenseData.set(response2.data);
            tempExpense.set(response2.data);
            $dateText = "Today"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getYesterday() {
        var currentDate = new Date();

        var yesterday = new Date(currentDate);
        yesterday.setDate(currentDate.getDate() - 1);

        var yesterdayString = yesterday.toISOString().split('T')[0];

        try {
            const response1 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?date=' + yesterdayString, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            incomeData.set(response1.data);
            tempIncome.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?date=' + yesterdayString, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            expenseData.set(response2.data);
            tempExpense.set(response2.data);
            $dateText = "Yesterday"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getMonth() {
        var currentDate = new Date();
        var year = currentDate.getMonth() + 1;

        try {
            const response1 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response1.data);
            tempIncome.set(response1.data);

            const response2 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response2.data);
            tempExpense.set(response2.data);
            $dateText = "This Month"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getLastMonth() {
        var currentDate = new Date();
        var year = currentDate.getMonth();

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            $dateText = "Last Month"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getLastYear() {
        var currentDate = new Date();
        var year = currentDate.getFullYear();

        try {
            const response1 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?year=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response1.data);
            tempIncome.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?year=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response2.data);
            tempExpense.set(response2.data);
            $dateText = "This Year"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    function filterByCategory(category) {
        console.log(category)
        let tempArr = $tempIncome.filter(income => income.incomeCategory.name === category);
        incomeData.set(tempArr);
    }

</script>

<div id="main-data" style="background-color: {$globalStyles.dashColor}; color: {$globalStyles.color}">
    <div id="data-header" style="background-color:{$globalStyles.mainColor}; color: {$globalStyles.altColor}">
        <span style="color: {$globalStyles.altColor}" contenteditable="false" bind:innerHTML={incomeAnalysisText}></span>

        <div id="dropdown-date">
            <button id="btn1" class="button" on:click={clickHandlerDate}>Filter by Date ▼</button>
            {#if isDateDropdownExpanded}
                <div id="date-list" transition:slide>
                    <div class="date-entry" on:click={() => getToday()}>Today</div>
                    <div class="date-entry" on:click={() => getYesterday()}>Yesterday</div>
                    <div class="date-entry" on:click={() => getMonth()}>This month</div>
                    <div class="date-entry" on:click={() => getLastMonth()}>Last month</div>
                    <!--                    <div on:click={() => console.log("Current quarter")}>Current quarter</div>-->
                    <div class="date-entry" on:click={() => getLastYear()}>This year</div>
                </div>
            {/if}
        </div>

        <div id="dropdown-category">
            <button id="btn2" class="button" on:click={clickHandlerCategory}>Filter by Category ▼</button>
            {#if isCategoryDropdownExpanded}
                <div id="date-list" transition:slide>
                    {#each $incomeTypes as income (income.id)}
                        {#if income.id !== undefined}
                            <div class="date-entry" on:click={() => filterByCategory(income.name)} value={income.id}>{income.name}</div>
                        {/if}
                    {/each}
                </div>
            {/if}
        </div>
    </div>
    <div id="data-menu">
        <div id="first-graph">
            <Graph1 />
        </div>
        <div id="second-graph">
            <Graph3 />
        </div>
        <Incomes />
    </div>

</div>

<style>
    #main-data {
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
        padding:0;
        display: flex;
        min-height: 0;
        height: 0;
        flex-direction: column;
        justify-content: stretch;
        align-items: stretch;
        flex: 1 1 auto;
    }

    /*#button {*/
    /*    background-color: #fff000;*/
    /*    border-radius: 12px;*/
    /*    color: #000;*/
    /*    cursor: pointer;*/
    /*    font-weight: bold;*/
    /*    padding: 10px 15px;*/
    /*    text-align: center;*/
    /*    transition: 200ms;*/
    /*    width: 100%;*/
    /*    box-sizing: border-box;*/
    /*    border: 0;*/
    /*    font-size: 16px;*/
    /*    user-select: none;*/
    /*    -webkit-user-select: none;*/
    /*    touch-action: manipulation;*/
    /*}*/

    /*#button:not(:disabled):hover,*/
    /*#button:not(:disabled):focus {*/
    /*    outline: 0;*/
    /*    background: #f4e603;*/
    /*    box-shadow: 0 0 0 2px rgba(0,0,0,.2), 0 3px 8px 0 rgba(0,0,0,.15);*/
    /*}*/

    /*#button:disabled {*/
    /*    filter: saturate(0.2) opacity(0.5);*/
    /*    -webkit-filter: saturate(0.2) opacity(0.5);*/
    /*    cursor: not-allowed;*/
    /*}*/

    #date-list {
        background-color: #007BFF;
        position:absolute;
        margin-top: 20px;
        max-height: 400px;
        overflow-y: scroll;
        border-radius: 20px;
        z-index:1;
    }

    .date-entry {
        padding: 10px;
        margin: 10px;
        background-color: black;
        color: white;
        border-radius: 20px;
        cursor: pointer;
    }

    .date-entry:hover {
        background-color: rgb(128, 128, 128);
    }


    /*.button {*/
    /*    font-size: large;*/
    /*    margin: 10px;*/
    /*    background-color: #007BFF;*/
    /*    color: #fff;*/
    /*    border: none;*/
    /*    border-radius: 20px;*/
    /*    line-height: 40px;*/
    /*    cursor: pointer;*/
    /*}*/

    /*.button:hover {*/
    /*    background-color: #0056b3;*/
    /*}*/

    .button {
        align-items: center;
        background-color: #0A66C2;
        border: 0;
        border-radius: 100px;
        box-sizing: border-box;
        color: #ffffff;
        cursor: pointer;
        display: inline-flex;
        font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto, "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell, "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Lucida Grande", Helvetica, Arial, sans-serif;
        font-size: 16px;
        font-weight: 600;
        justify-content: center;
        line-height: 20px;
        max-width: 480px;
        min-height: 40px;
        min-width: 0px;
        overflow: hidden;
        padding: 0px;
        padding-left: 20px;
        padding-right: 20px;
        text-align: center;
        touch-action: manipulation;
        transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s, box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s, color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
        user-select: none;
        -webkit-user-select: none;
        vertical-align: middle;
    }

    .button:hover,
    .button:focus {
        background-color: #16437E;
        color: #ffffff;
    }

    .button:active {
        background: #09223b;
        color: rgb(255, 255, 255, .7);
    }

    .button:disabled {
        cursor: not-allowed;
        background: rgba(0, 0, 0, .08);
        color: rgba(0, 0, 0, .3);
    }

    /*#category-list {*/
    /*    background-color: #8BD17C;*/
    /*    position:absolute;*/
    /*    z-index:1;*/
    /*}*/

    ::-webkit-scrollbar {
        width: 10px;
    }

    /* Track */
    ::-webkit-scrollbar-track {
        background: #f1f1f1;
        border-radius: 10px;
    }

    /* Handle */
    ::-webkit-scrollbar-thumb {
        background: #888;
        border-radius: 10px;
    }

    /* Handle on hover */
    ::-webkit-scrollbar-thumb:hover {
        background: #555;
    }

    #data-header {
        background-color: black;
        min-height: 50px;
        padding-left: 30px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-around;
        border-top-left-radius: 20px;
        border-top-right-radius: 20px;
        font-size: larger;
        margin-bottom: 5px;
        /*border: #8BD17C 2px solid;*/
    }

    #data-menu {
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
        display:flex;
        /*padding:10px;*/
        flex-direction: row-reverse;
        justify-content: space-between;
        align-items: stretch;
        flex: 1;
        height: 0;
        min-height: 0;
    }

    #first-graph {
        display: flex;
        flex-direction: column;
        align-self: stretch;
        flex-grow: 1;
        min-width: 0;
        min-height:0;
    }

    #second-graph {
        display: flex;
        flex-direction: column;
        align-self: stretch;
        flex-grow: 1;
        min-width: 0;
        min-height:0;
    }

</style>