<script>
        import { getCookie } from "svelte-cookie";
        import { onMount } from "svelte";
        import ExpenseDashboard from "./ExpenseDashboard.svelte";
        import IncomeDashboard from "./IncomeDashboard.svelte";
        import Settings from "./Settings.svelte";
        import {
            incomeData,
            expenseData,
            incomeTypes,
            expenseTypes,
            selectedTab,
            monthIncome,
            monthExpense,
            tempExpense,
            tempIncome
        } from "../stores.js";
        import {globalStyles} from "../styles.js";

        let componentStyles;

        $: {
                console.log("got here")
                componentStyles = $globalStyles;
        }

        import axios from "axios";
        import Statistics from "./Statistics.svelte";
        import AdminPanel from "./AdminPanel.svelte";
        import Profile from "./Profile.svelte";

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
                const currentDate = new Date();
                const currentMonth = currentDate.getMonth() + 1;
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

                tempExpense.set(expenseResponse.data);
                tempIncome.set(incomeResponse.data);

                monthIncome.set(incomeResponse.data);
                monthExpense.set(expenseResponse.data);
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
                {:else if $selectedTab === 'statistics'}
                        <Statistics />
                {:else if $selectedTab === 'admin'}
                        <AdminPanel />
                {:else if $selectedTab === 'profile'}
                        <Profile />
                {/if}
</div>

<style>

        #dashboard {
                font-family: 'Source Sans Pro', sans-serif;
                border-radius: 20px;
                margin: 20px;
                min-width: 100px;
                display: flex;
                flex: 1 1 auto;
                flex-direction: column;
                align-items: stretch;
                justify-content: stretch;
                transition: all 0.3s cubic-bezier(.25,.8,.25,1);
        }

        @media only screen and (max-width: 900px) {
            #dashboard {
                margin: 0;
                flex-wrap: wrap;
                width: 100%;
            }
        }
</style>
