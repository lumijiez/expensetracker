<script>
        import { getCookie } from "svelte-cookie";
        import { onMount } from "svelte";
        import ExpenseDashboard from "./ExpenseDashboard.svelte";
        import IncomeDashboard from "./IncomeDashboard.svelte";
        import { incomeData, expenseData, incomeTypes, expenseTypes, selectedTab } from "../stores.js";

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
        <div id="dashboard">
                {#if $selectedTab === 'expenses'}
                        <ExpenseDashboard {expenseData} {expenseTypes} />
                {:else if $selectedTab === 'incomes'}
                        <IncomeDashboard {incomeData} {incomeTypes} />
                {/if}
        </div>
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
