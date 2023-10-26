<script>
	import DashHeader from "./other/DashHeader.svelte";
	import DataMenu from "./other/DataMenu.svelte";
	import QuickInfobar from "./other/QuickInfobar.svelte";
    import { getCookie } from "svelte-cookie";
    import {onMount} from "svelte";

    import {incomeData, expenseData, incomeTypes, expenseTypes} from "../stores.js";

    import axios from "axios";


    onMount(async () => {
            const token = getCookie('access_token');

            if (token === '') {
                    window.location.href = '/auth/login';
                    return;
            }

            const config = {
                    headers: {
                            'Authorization': `Bearer ${token}`
                    }
            };

            try {
                    const [incomeResponse, expenseResponse, incomeTypesResponse, expenseTypesResponse] = await Promise.all([
                            axios.get('http://localhost:8081/incomes/personal-incomes', config),
                            axios.get('http://localhost:8081/expenses/personal-expenses', config),
                            axios.get('http://localhost:8081/incomes/categories', config),
                            axios.get('http://localhost:8081/expenses/categories', config)
                    ]);

                    incomeData.set(incomeResponse.data);
                    expenseData.set(expenseResponse.data);
                    incomeTypes.set(incomeTypesResponse.data);
                    expenseTypes.set(expenseTypesResponse.data);
            } catch (error) {
                    console.error('Error:', error);
            }
    });
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