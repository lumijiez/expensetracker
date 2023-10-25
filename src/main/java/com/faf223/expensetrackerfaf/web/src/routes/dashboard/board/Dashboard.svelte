<script>
	import DashHeader from "./DashHeader.svelte";
	import DataMenu from "./DataMenu.svelte";
	import QuickInfobar from "./QuickInfobar.svelte";
    import { getCookie } from "svelte-cookie";
    import {onMount} from "svelte";

    onMount(() => {
            if (getCookie('access_token') === null ) {
                    window.location.href = '/auth/login';
            }
    })
</script>

<div id="dashboard">
        <DashHeader />
        <QuickInfobar />
        <DataMenu />
</div>

<style>
    #dashboard {
        font-family: 'Source Sans Pro', sans-serif;
        background-color: rgb(245,242,243); 
        border-radius: 20px;  
        margin: 20px;
        min-width: 100px;
        display: flex;
        flex:1 1 auto;
        flex-direction: column;
        align-items: stretch;
        justify-content: stretch;
    }
</style>