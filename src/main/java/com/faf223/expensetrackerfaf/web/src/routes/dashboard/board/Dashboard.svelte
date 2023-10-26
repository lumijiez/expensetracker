<script>
	import DashHeader from "./other/DashHeader.svelte";
	import DataMenu from "./other/DataMenu.svelte";
	import QuickInfobar from "./other/QuickInfobar.svelte";
    import { getCookie } from "svelte-cookie";
    import {onMount} from "svelte";
    import {writable} from "svelte/store";
    import axios from "axios";

    const incomeData = writable([]);
    const expenseData = writable([]);

    onMount(async () => {
            if (getCookie('access_token') === null ) {
                    window.location.href = '/auth/login';
            }

            try {
                    const response = await axios.get('http://localhost:8081/incomes/personal-incomes', config);
                    incomeData.set(response.data);
            } catch (error) {
                    console.error('Error fetching income data:', error);
            }

            try {
                    const response = await axios.get('http://localhost:8081/expenses/personal-expenses', config);
                    expenseData.set(response.data);
            } catch (error) {
                    console.error('Error fetching expense data:', error);
            }

            console.log(getCookie('access_token'));
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