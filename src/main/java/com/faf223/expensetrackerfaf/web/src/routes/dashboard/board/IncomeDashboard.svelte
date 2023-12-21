<script>
    import DashHeader from "./incomes/other/DashHeader.svelte";
    import QuickInfobar from "./incomes/other/QuickInfobar.svelte";
    import Incomes from "./incomes/infolists/Incomes.svelte";
    import Graph3 from "./incomes/graphs/Graph3.svelte";
    import Graph2 from "./incomes/graphs/Graph2.svelte";
</script>

<div class="incomeContainer">
    <div class="dataHalf">
        <div>
            <DashHeader />
            <QuickInfobar />
        </div>

        <div class="graphs">
            <Graph3 />
            <Graph2 />
        </div>
    </div>
    <Incomes />
</div>

<style>
    @media only screen and (max-width: 900px) {
        .incomeContainer {
            flex-wrap: wrap;
            flex: 1 1 auto;
            width: 100%;
        }

        .graphs {
            flex-wrap: wrap;
        }
    }

    .graphs {
        display:flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: stretch;
        min-width: 0;
        min-height: 0;
        height: 100% !important;
        width: 100% !important;
        flex: 1 1 auto;
    }
    .incomeContainer {
        display: flex;
        height: 100%;
        background-color: #172233;
        flex-direction: row;
        justify-content: space-between;
    }

    .dataHalf {
        display:flex;
        min-height: 0;
        min-width: 0;
        flex-direction: column;
        flex: 1 1 auto;
        background-color: #212942;
        border-radius: 20px;
        /*padding: 10px;*/
    }
</style>

