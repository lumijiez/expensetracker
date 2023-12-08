<script>
        import { getCookie } from "svelte-cookie";
        import { onMount } from "svelte";
        import ExpenseDashboard from "./ExpenseDashboard.svelte";
        import IncomeDashboard from "./IncomeDashboard.svelte";
        import Settings from "./Settings.svelte";
        import { incomeData, expenseData, incomeTypes, expenseTypes, selectedTab } from "../stores.js";
        import {globalStyles} from "../styles.js";

        let componentStyles;

        $: {
                console.log("got here")
                componentStyles = $globalStyles;
        }

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
                var currentDate = new Date();
                var currentMonth = currentDate.getMonth() + 1;
                const [incomeResponse, expenseResponse, incomeTypesResponse, expenseTypesResponse] = await Promise.all([
                    axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + currentMonth , config),
                    axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + currentMonth, config),
                    axios.get('https://trackio.online:8081/incomes/categories', config),
                    axios.get('https://trackio.online:8081/expenses/categories', config)
                ]);

                console.log("Data", incomeResponse.data);

                incomeData.set(incomeResponse.data);
                expenseData.set(expenseResponse.data);
                incomeTypes.set(incomeTypesResponse.data);
                expenseTypes.set(expenseTypesResponse.data);
            } catch (error) {
                console.error('Error:', error);
            }
        });
</script>

<svelte:head>
    <link rel="icon" type="image/x-icon" href="../favicon.png" />
    <title>Track.IO</title>
</svelte:head>

<div id="dashboard" style="background-color: {componentStyles.dashColor}; color: {componentStyles.color}">
                {#if $selectedTab === 'expenses'}
                        <ExpenseDashboard />
                {:else if $selectedTab === 'incomes'}
                        <IncomeDashboard />
                {:else if $selectedTab === 'settings'}
                        <Settings />
                {/if}
</div>

<style>

        #dashboard {
                font-family: 'Source Sans Pro', sans-serif;
                border-radius: 20px;
                margin: 20px;
                padding: 20px 20px 0;
                min-width: 100px;
                display: flex;
                flex: 1 1 auto;
                flex-direction: column;
                align-items: stretch;
                justify-content: stretch;
                transition: all 0.3s cubic-bezier(.25,.8,.25,1);
        }
</style>
