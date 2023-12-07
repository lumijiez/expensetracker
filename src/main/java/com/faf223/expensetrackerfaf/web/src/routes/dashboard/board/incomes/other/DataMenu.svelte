<script>
    import Graph1 from '../graphs/Graph1.svelte';
    import Graph3 from '../graphs/Graph3.svelte';
    import Incomes from "../infolists/Incomes.svelte";
    import {globalStyles} from "../../../styles.js";
    import { slide } from 'svelte/transition'
    import {incomeTypes} from "../../../stores.js";

    let isDateDropdownExpanded = false
    let isCategoryDropdownExpanded = false

    function clickHandlerDate() {
        isDateDropdownExpanded = !isDateDropdownExpanded
    }

    function clickHandlerCategory() {
        isCategoryDropdownExpanded = !isCategoryDropdownExpanded;
    }

</script>

<div id="main-data" style="background-color: {$globalStyles.dashColor}; color: {$globalStyles.color}">
    <div id="data-header" style="background-color:{$globalStyles.mainColor}; color: {$globalStyles.altColor}">
        <span>Revenue Analysis</span>

        <div id="dropdown-date">
            <button id="button" on:click={clickHandlerDate}>Filter by Date:</button>
            {#if isDateDropdownExpanded}
                <div id="date-list" transition:slide>
                    <div on:click={() => console.log("Today")}>Today</div>
                    <div on:click={() => console.log("Yesterday")}>Yesterday</div>
                    <div on:click={() => console.log("Last week")}>Last week</div>
                    <div on:click={() => console.log("Last month")}>Last month</div>
                    <div on:click={() => console.log("Current quarter")}>Current quarter</div>
                    <div on:click={() => console.log("This year")}>This year</div>
                </div>
            {/if}
        </div>

        <div id="dropdown-category">
            <button id="button" on:click={clickHandlerCategory}>Filter by Category:</button>
            {#if isCategoryDropdownExpanded}
                <div id="category-list" transition:slide>
                    {#each $incomeTypes as income (income.id)}
                        {#if income.id !== undefined}
                            <option value={income.id}>{income.name}</option>
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

    #button {
        font-size: large;
        background-color: #007BFF;
        color: #fff;
        border: none;
        border-radius: 20px;
        line-height: 40px;
        cursor: pointer;
    }

    #button:hover {
        background-color: #0056b3;
    }

    #date-list {
        background-color: #8BD17C;
        position:absolute;
        z-index:1;
    }

    #category-list {
        background-color: #8BD17C;
        position:absolute;
        z-index:1;
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