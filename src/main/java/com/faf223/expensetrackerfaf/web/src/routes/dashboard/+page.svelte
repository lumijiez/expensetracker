<script>
    import Dashboard from './board/Dashboard.svelte';
    import SideMenu from './menu/SideMenu.svelte';
    import {selectedTab} from "./stores.js";
    import {globalStyles} from "./styles.js";
    import StickyMenu from "./menu/StickyMenu.svelte";
    import {onMount} from "svelte";

    function handleTabClick(tab) {
        selectedTab.set(tab);
        // $isCategorizedIncome = false;
        // $isCategorizedExpense = false;
    }

    let screenWidth;

    onMount(() => {
        screenWidth = window.innerWidth;
        const handleResize = () => {
            console.log(screenWidth);
            screenWidth = window.innerWidth;
        };

        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    });

</script>

<div id="wrapper" style="background-color: {$globalStyles.mainColor}">
    {#if screenWidth < 900}
        <StickyMenu onTabClick={handleTabClick} />
    {:else}
        <SideMenu onTabClick={handleTabClick} />
    {/if}
    <Dashboard />
</div>

<style>
    @import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400');
    @import url('https://fonts.googleapis.com/css?family=Inconsolata');

    #wrapper {
        padding: 0;
        font-family: Inconsolata,"Source Sans Pro",sans-serif;
        margin: 0;
        display: flex;
        align-items: stretch;
        min-height: 100vh;
        max-height: 100%;
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
    }

    @media only screen and (max-width: 900px) {
        #wrapper {
            flex-direction: column;
        }
    }
</style>