<script>
    import Graph1 from './graphs/Graph1.svelte';
    import Graph2 from './graphs/Graph2.svelte';
    import Graph3 from './graphs/Graph3.svelte';
    import Graph4 from './graphs/Graph4.svelte';
    import Graph5 from './graphs/Graph5.svelte';
</script>

<div id="dataMenu">
    <div id="twoVertical">
            <Graph1 />
            <Graph2 />
    </div>
    <div id="oneVertical">
            <Graph3 />
    </div>
    <div id="twoHorizontal">
            <Graph4 />
            <Graph5 />
    </div>
</div>

<style>
    #dataMenu {
        border-bottom-left-radius: 20px;
        border-bottom-right-radius: 20px;
        background-color: rgb(245,242,243);
        display:flex;
        padding:10px;
        flex-direction: row-reverse;
        justify-content: stretch;
        align-items: stretch;
        flex-grow: 1;
    }



    #twoVertical {
        display: flex;
        flex-direction: column;
        align-self: stretch;
        flex-grow: 1;
        min-width: 0;
        min-height:0;
    }

    #oneVertical {
        display: flex;
        flex-direction: column;
        align-self: stretch;
        flex-grow: 1;
        min-width: 0;
        min-height:0;
    }

    #twoHorizontal {
        display: flex;
        flex-direction: column;
        align-self: stretch;
        flex-grow: 1;
        min-width: 0;
        min-height:0;
    }

</style>