<script>
	import DashHeader from "./other/DashHeader.svelte";
	import DataMenu from "./other/DataMenu.svelte";
	import QuickInfobar from "./other/QuickInfobar.svelte";
    import { getCookie } from "svelte-cookie";
    import {onMount} from "svelte";

    import {incomeData} from "../stores.js";
    import {expenseData} from "../stores.js";
    import {incomeTypes} from "../stores.js";

    import axios from "axios";


    onMount(() => {
            if (getCookie('access_token') === null) {
                    window.location.href = '/auth/login';
            }

            const token = getCookie('access_token');
            const config = {
                    headers: {
                            'Authorization': `Bearer ${token}`
                    }
            };

            const incomePromise = axios.get('http://localhost:8081/incomes/personal-incomes', config)
                    .then(response => {
                            incomeData.set(response.data);
                            console.log("Received Income Data");
                    })
                    .catch(error => console.error('Error fetching income data:', error));

            const expensePromise = axios.get('http://localhost:8081/expenses/personal-expenses', config)
                    .then(response => {
                            expenseData.set(response.data);
                            console.log("Received Expense Data");
                    })
                    .catch(error => console.error('Error fetching expense data:', error));

            const incomeTypesPromise = axios.get('http://localhost:8081/incomes/categories', config)
                    .then(response => {
                            incomeTypes.set(response.data);
                            console.log("Received Income Type Data");
                    })
                    .catch(error => console.error('Error:', error));

            Promise.all([incomePromise, expensePromise, incomeTypesPromise])
                    .then(() => {
                            console.log(getCookie('access_token'));
                    });
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