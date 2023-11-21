<script>
    import ContentIncome from "./ContentIncome.svelte";
    import { incomeData } from "../../../stores.js";
    import { globalStyles } from "../../../styles.js";
</script>

<div id="incomeInfo" style="background-color: {$globalStyles.mainColor}">
    <ContentIncome />
    <div id="listContainer" style="color: {$globalStyles.color}">
        <ul>
            {#each $incomeData as item}
                <li>
                    {item.incomeCategory ? `${item.incomeCategory.name}: ` : `${item.incomeCategory.name}: `}
                    {item.incomeCategory ? `+${item.amount}$` : `-${item.amount}$`}
                    {`${item.date}`}
                </li>
            {/each}
        </ul>
    </div>
</div>

<style>

    #incomeInfo {
        min-width: 300px;
        min-height: 0;
        background-color: #212942;
        color: white;
        border-radius: 0 0 10px 10px;
        margin: 0 0 10px 0;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }

    #listContainer {
        flex: 1 1 auto;
        overflow-y: auto;
        min-height: 0;
        padding: 0 10px 10px;
        margin: 0 0 10px;
        box-sizing: border-box;
        border-radius: 0 0 10px 10px;
    }

    #listContainer::-webkit-scrollbar {
        width: 0;
    }

    #listContainer::-webkit-scrollbar-thumb {
        background-color: transparent;
    }

    #listContainer::-webkit-scrollbar-track {
        background-color: transparent;
    }

    #listContainer ul {
        list-style: none;
        padding: 0;
        border-radius: 0 0 10px 10px;
    }

    #listContainer li {
        margin-bottom: 20px;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
    }

    #listContainer li:hover {
        box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    }
</style>
