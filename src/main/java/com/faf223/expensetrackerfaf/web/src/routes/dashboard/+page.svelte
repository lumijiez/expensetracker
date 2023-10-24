<script>
    import Dashboard from './board/Dashboard.svelte';
    import SideMenu from './menu/SideMenu.svelte';
</script>

<div id="wrapper">
    <SideMenu />
    <Dashboard />
</div>

<style>
    @import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400');

    #wrapper {
        background-color: rgb(23,34,51);
        display: flex;
        align-items: stretch;
        min-height: 100vh;
        max-height: 100%;
    }
</style>