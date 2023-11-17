<script>
    import { onMount, afterUpdate } from 'svelte';
    import { incomeData } from "../../../stores.js";
    import ContentIncome from "./contents/ContentIncome.svelte";

    let parentHeight;
    let listParentHeight;

    async function updateInfo() {
        parentHeight = document.querySelector('#expenseInfo').offsetHeight;
        listParentHeight = document.querySelector('#expenseList').offsetHeight;
    }

    onMount(updateInfo);
    afterUpdate(updateInfo);
</script>

<div id="incomeInfo" style="max-height: {parentHeight}px;">
    <ContentIncome />

    <div id="incomeList" style="max-height: {listParentHeight}px;">
        <ul>
            {#each $incomeData as item}
                <li>
                    {item.incomeCategory ? `${item.incomeCategory.name}: ` : `${item.expenseCategory.name}: `}
                    {item.incomeCategory ? `+${item.amount}$` : `-${item.amount}$`}
                    {`${item.date}`}
                </li>
            {/each}
        </ul>
    </div>
</div>

<style>
    #incomeInfo {
        display: flex;
        flex-direction: column;
        min-width: 300px;
        background-color: #212942;
        color:white;
        border-radius: 10px;
        margin: 10px;
    }

    #incomeList {
        scrollbar-width: none;
        flex: 1;
        border-radius: 10px;
        margin: 10px;
        overflow-y: auto;
        max-height: 100%;
    }

    ul {
        list-style: none;
        padding: 0;
        color: black;
    }

    li {
        margin-bottom: 20px;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
    }

    li:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }

    #incomeList::-webkit-scrollbar {
        display: none;
    }
</style>
