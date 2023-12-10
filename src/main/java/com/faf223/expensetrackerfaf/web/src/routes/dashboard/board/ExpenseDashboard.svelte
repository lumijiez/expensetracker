<script>
    import DashHeader from "./expenses/other/DashHeader.svelte";
    import QuickInfobar from "./expenses/other/QuickInfobar.svelte";
    import Expenses from "./expenses/infolists/Expenses.svelte";
</script>

<div class="expenseContainer">
    <div class="dataHalf">
        <DashHeader />
        <QuickInfobar />
    </div>
    <Expenses />
</div>

<style>
    @media only screen and (max-width: 900px) {
        .expenseContainer {
            flex-wrap: wrap;
            flex: 1 1 auto;
            width: 100%;
        }
    }

    .expenseContainer {
        display: flex;
        height: 100%;
        flex-direction: row;
        justify-content: space-between;
    }

    .dataHalf {
        flex: 1 1 auto;
        background-color: #8BD17C;
    }
</style>

