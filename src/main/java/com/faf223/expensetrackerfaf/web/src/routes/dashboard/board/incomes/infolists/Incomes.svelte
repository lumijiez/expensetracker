<script>
    import ContentIncome from "./ContentIncome.svelte";
    import {
        dateText,
        expenseData,
        incomeData,
        tempExpense,
        tempIncome,
        monthIncome,
        monthExpense,
        isCategorizedExpense,
        categorizedExpense,
        currencyLabel, copyExpenseData, copyIncomeData, incomeCategoryLabel, categorizedIncome, isCategorizedIncome, incomeTypes
    } from "../../../stores.js";
    import {globalStyles} from "../../../styles.js";
    import {onMount} from "svelte";
    import axios from "axios";
    import {getCookie} from "svelte-cookie";
    import {slide} from 'svelte/transition'
    import EditEntry from "../util/EditEntry.svelte";

    const textToIcon = {
        'Interest': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"20\" viewBox=\"0 0 640 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M539.7 237.3c3.1-12.3 4.3-24.8 4.3-37.4C544 107.4 468.6 32 376.1 32c-77.2 0-144.6 53-163 127.8-15.3-13.2-34.9-20.5-55.2-20.5-46.3 0-84 37.7-84 84 0 7.4 .9 15 3.1 22.4-42.9 20.2-70.8 63.7-70.8 111.2C6.2 424.8 61.7 480 129.4 480h381.2c67.7 0 123.2-55.2 123.2-123.2 0-56.4-38.9-106-94.1-119.5zM199.9 401.6c0 8.3-7 15.3-15.3 15.3H153.6c-8.3 0-15.3-7-15.3-15.3V290.6c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v110.9zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V270.1c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v131.5zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V238.8c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v162.7zm87 0c0 8.3-7 15.3-15.3 15.3h-28.5c-8.3 0-15.3-7-15.3-15.3V176.9c0-8.6 7-15.6 15.3-15.6h28.5c8.3 0 15.3 7 15.3 15.6v224.6z\"/></svg>",
        'Salary': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>",
        'Freelance Income': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>",
        'Investment Income': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"20\" viewBox=\"0 0 640 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M539.7 237.3c3.1-12.3 4.3-24.8 4.3-37.4C544 107.4 468.6 32 376.1 32c-77.2 0-144.6 53-163 127.8-15.3-13.2-34.9-20.5-55.2-20.5-46.3 0-84 37.7-84 84 0 7.4 .9 15 3.1 22.4-42.9 20.2-70.8 63.7-70.8 111.2C6.2 424.8 61.7 480 129.4 480h381.2c67.7 0 123.2-55.2 123.2-123.2 0-56.4-38.9-106-94.1-119.5zM199.9 401.6c0 8.3-7 15.3-15.3 15.3H153.6c-8.3 0-15.3-7-15.3-15.3V290.6c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v110.9zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V270.1c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v131.5zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V238.8c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v162.7zm87 0c0 8.3-7 15.3-15.3 15.3h-28.5c-8.3 0-15.3-7-15.3-15.3V176.9c0-8.6 7-15.6 15.3-15.6h28.5c8.3 0 15.3 7 15.3 15.6v224.6z\"/></svg>",
        'Comission': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128v64c0 8.8 7.4 15.7 15.7 18.6C34.5 217.1 48 235 48 256s-13.5 38.9-32.3 45.4C7.4 304.3 0 311.2 0 320v64c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V320c0-8.8-7.4-15.7-15.7-18.6C541.5 294.9 528 277 528 256s13.5-38.9 32.3-45.4c8.3-2.9 15.7-9.8 15.7-18.6V128c0-35.3-28.7-64-64-64H64zm64 112l0 160c0 8.8 7.2 16 16 16H432c8.8 0 16-7.2 16-16V176c0-8.8-7.2-16-16-16H144c-8.8 0-16 7.2-16 16zM96 160c0-17.7 14.3-32 32-32H448c17.7 0 32 14.3 32 32V352c0 17.7-14.3 32-32 32H128c-17.7 0-32-14.3-32-32V160z\"/></svg>",
        'Sold Products': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>",
        'Gifts': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M190.5 68.8L225.3 128H224 152c-22.1 0-40-17.9-40-40s17.9-40 40-40h2.2c14.9 0 28.8 7.9 36.3 20.8zM64 88c0 14.4 3.5 28 9.6 40H32c-17.7 0-32 14.3-32 32v64c0 17.7 14.3 32 32 32H480c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32H438.4c6.1-12 9.6-25.6 9.6-40c0-48.6-39.4-88-88-88h-2.2c-31.9 0-61.5 16.9-77.7 44.4L256 85.5l-24.1-41C215.7 16.9 186.1 0 154.2 0H152C103.4 0 64 39.4 64 88zm336 0c0 22.1-17.9 40-40 40H288h-1.3l34.8-59.2C329.1 55.9 342.9 48 357.8 48H360c22.1 0 40 17.9 40 40zM32 288V464c0 26.5 21.5 48 48 48H224V288H32zM288 512H432c26.5 0 48-21.5 48-48V288H288V512z\"/></svg>",
        'Government Payments': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>"
    };

    let isDateDropdownExpanded = false
    let isCategoryDropdownExpanded = false
    let isCurrencyDropdownExpanded = false
    let isFilterDown = false;

    let dropdownStates = {};
    let deleteDropdownStates = {}

    $: {
        dropdownStates = {};
        deleteDropdownStates = {};
        $incomeData.toReversed().forEach(data => {
            dropdownStates[data.incomeId] = false;
            deleteDropdownStates[data.incomeId] = false;
        });
    }

    function clickFilter() {
        isDateDropdownExpanded = false;
        isCategoryDropdownExpanded = false;
        isFilterDown = !isFilterDown;
        isCurrencyDropdownExpanded = false;
    }
    function clickHandlerDate() {
        isDateDropdownExpanded = !isDateDropdownExpanded
        isCategoryDropdownExpanded = false;
    }

    function clickHandlerCategory() {
        isCategoryDropdownExpanded = !isCategoryDropdownExpanded;
        isDateDropdownExpanded = false;
    }

    function clickHandlerCurrency() {
        isCurrencyDropdownExpanded = !isCurrencyDropdownExpanded;
        isCategoryDropdownExpanded = false;
        isDateDropdownExpanded = false;
        isFilterDown = false;
    }

    function clickItemHandler(id) {
        dropdownStates[id] = !dropdownStates[id];
        if (deleteDropdownStates[id] === true) deleteDropdownStates[id] = false;
    }

    function clickDeleteHandler(id) {
        deleteDropdownStates[id] = !deleteDropdownStates[id];
        if (dropdownStates[id] === true) dropdownStates[id] = false;
    }

    function clickOutsideHandler(event) {
        const isDateButton = event.target.closest("#incomeInfo");

        if (!isDateButton) {
            isFilterDown = false;
            isCategoryDropdownExpanded = false;
            isDateDropdownExpanded = false;
            isCurrencyDropdownExpanded = false;
        }
    }

    onMount(() => {
        $isCategorizedExpense = false;
        $isCategorizedIncome = false;

        document.body.addEventListener("click", clickOutsideHandler);

        return () => {
            document.body.removeEventListener("click", clickOutsideHandler);
        };
    });

    async function getToday() {
        const currentDate = new Date();
        const currentDay = currentDate.toISOString().split('T')[0];
        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?date=' + currentDay, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            copyExpenseData.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?date=' + currentDay, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            copyIncomeData.set(response1.data);
            $dateText = "Today"

            $isCategorizedExpense = false;
            categorizedExpense.set([]);
            changeCurrency($currencyLabel);
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getYesterday() {
        const currentDate = new Date();

        const yesterday = new Date(currentDate);
        yesterday.setDate(currentDate.getDate() - 1);

        const yesterdayString = yesterday.toISOString().split('T')[0];

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?date=' + yesterdayString, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            copyExpenseData.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?date=' + yesterdayString, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            copyIncomeData.set(response2.data);
            $dateText = "Yesterday"
            changeCurrency($currencyLabel);
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getMonth() {
        const currentDate = new Date();
        const year = currentDate.getMonth() + 1;

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            monthExpense.set(response1.data);
            copyExpenseData.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            monthIncome.set(response2.data);
            copyIncomeData.set(response2.data);
            $dateText = "This Month"

            $isCategorizedExpense = false;
            categorizedExpense.set([]);
            changeCurrency($currencyLabel);
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getLastMonth() {
        const currentDate = new Date();
        const year = currentDate.getMonth();

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data)
            monthExpense.set(response1.data);
            copyExpenseData.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            monthIncome.set(response2.data);
            copyIncomeData.set(response2.data);
            $dateText = "Last Month"

            $isCategorizedExpense = false;
            categorizedExpense.set([]);
            changeCurrency($currencyLabel);
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getLastYear() {
        const currentDate = new Date();
        const year = currentDate.getFullYear();

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?year=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            monthExpense.set(response1.data);
            copyExpenseData.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?year=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            monthIncome.set(response2.data);
            copyIncomeData.set(response2.data);
            $dateText = "This Year"

            $isCategorizedExpense = false;
            categorizedExpense.set([]);
            changeCurrency($currencyLabel);
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    function filterByCategory(category) {
        $isCategorizedIncome = true;
        $incomeCategoryLabel = category;
        console.log($isCategorizedIncome);
        let tempArr = $tempIncome.filter(income => income.incomeCategory.name === category);
        categorizedIncome.set(tempArr);
        incomeData.set(tempArr);
    }

    function getAll() {
        categorizedIncome.set([]);
        $incomeCategoryLabel = "Category";
        $isCategorizedIncome = false;
        console.log($isCategorizedIncome);
        incomeData.set($tempIncome);
    }

    function doNothing() {

    }

    function changeCurrency(currency) {
        if (currency === 'USD') {
            expenseData.set($copyExpenseData);
            incomeData.set($copyIncomeData);
            $currencyLabel = "USD";
        }
        if (currency === 'MDL') {
            const tempData1 = $copyExpenseData.map(expense => ({
                ...expense,
                amount: expense.amount / 0.056
            }));
            expenseData.set(tempData1);

            const tempData2 = $copyIncomeData.map(income => ({
                ...income,
                amount: income.amount / 0.056
            }));
            incomeData.set(tempData2);
            $currencyLabel = "MDL";
        }
        if (currency === 'EUR') {
            const tempData1 = $copyExpenseData.map(expense => ({
                ...expense,
                amount: expense.amount / 1.08
            }));
            expenseData.set(tempData1);

            const tempData2 = $copyIncomeData.map(income => ({
                ...income,
                amount: income.amount / 1.08
            }));
            incomeData.set(tempData2);
            $currencyLabel = "EUR";
        }

        if (currency === 'GBP') {
            const tempData1 = $copyExpenseData.map(expense => ({
                ...expense,
                amount: expense.amount / 1.26
            }));
            expenseData.set(tempData1);

            const tempData2 = $copyIncomeData.map(income => ({
                ...income,
                amount: income.amount / 1.26
            }));
            incomeData.set(tempData2);
            $currencyLabel = "GBP";
        }
    }
</script>

<div id="incomeInfo" style="background-color: {$globalStyles.mainColor}">
    <ContentIncome />
    <div style="display: flex; justify-content: space-around">

        <div id="dropdown" style="margin: 10px; display: flex; justify-content: space-between">
            <button id="btn1" class="button" on:click={clickFilter}>Filter ▼</button>
        </div>

        <div id="dropdown-currency" style="margin: 10px;">
            <button id="btn2" class="button" on:click={clickHandlerCurrency}>Currency ▼</button>
        </div>
    </div>

    <div>
        {#if isFilterDown}
            <div style="margin: 10px; display: flex; justify-content: space-between" transition:slide>
                <div id="dropdown-date" style="margin: 10px;">
                    <button id="btn3" class="button" on:click={clickHandlerDate}>Filter by Date ▼</button></div>

                <div id="dropdown-category" style="margin: 10px;">
                    <button id="btn4" class="button" on:click={clickHandlerCategory}>Filter by Category ▼</button>
                </div>
            </div>
        {/if}

        {#if isDateDropdownExpanded}
            <div id="date-list" transition:slide>
                <div class="date-entry" on:click={() => getToday()} role="button" tabindex="0"
                     on:keydown={doNothing}>Today
                </div>
                <div class="date-entry" on:click={() => getYesterday()} role="button" tabindex="0"
                     on:keydown={doNothing}>Yesterday
                </div>
                <div class="date-entry" on:click={() => getMonth()} role="button" tabindex="0"
                     on:keydown={doNothing}>This month
                </div>
                <div class="date-entry" on:click={() => getLastMonth()} role="button" tabindex="0"
                     on:keydown={doNothing}>Last month
                </div>
                <div class="date-entry" on:click={() => getLastYear()} role="button" tabindex="0"
                     on:keydown={doNothing}>This year
                </div>
            </div>
        {/if}

        {#if isCategoryDropdownExpanded}
            <div id="date-list" transition:slide>
                <div class="date-entry" on:click={() => getAll()} role="button"
                     tabindex="0" on:keydown={doNothing}>All</div>
                {#each $incomeTypes as income (income.id)}
                    {#if income.id !== undefined}
                        <div class="date-entry" on:click={() => filterByCategory(income.name)} role="button"
                             tabindex="0" on:keydown={doNothing}>{income.name}</div>
                    {/if}
                {/each}
            </div>
        {/if}

        {#if isCurrencyDropdownExpanded}
            <div id="date-list" transition:slide>
                <div class="date-entry" on:click={() => changeCurrency("MDL")} role="button"
                     tabindex="0" on:keydown={doNothing}>MDL Leu</div>
                <div class="date-entry" on:click={() => changeCurrency("GBP")} role="button"
                     tabindex="0" on:keydown={doNothing}>GBP Pound</div>
                <div class="date-entry" on:click={() => changeCurrency("USD")} role="button"
                     tabindex="0" on:keydown={doNothing}>USD Dollar</div>
                <div class="date-entry" on:click={() => changeCurrency("EUR")} role="button"
                     tabindex="0" on:keydown={doNothing}>EUR Euro</div>
            </div>
        {/if}
    </div>
    <div id="listContainer" style="color: {$globalStyles.color}">
        <ul>
            {#each $incomeData.toReversed() as item (item.incomeId)}
                <li style="display:flex; flex-direction: column; justify-content: space-between; color: {$globalStyles.color}">
                    <div style="display:flex; flex-direction: row; justify-content: space-between; align-items: center;">
                    <div>
                        {#if textToIcon[item.incomeCategory.name]}
                        {@html textToIcon[item.incomeCategory.name]}
                    {/if}
                        <span style="font-weight: bold">{item.incomeCategory ? `${item.incomeCategory.name}: ` : `${item.expenseCategory.name}: `}</span>
                    <span style="font-weight:bold; margin-right: 10px; color: limegreen; font-size: larger">{item.incomeCategory ? `+${item.amount.toFixed(2)} ${$currencyLabel}` : `-${item.amount.toFixed(2)} ${$currencyLabel}`}</span>
                    </div>
                        <div style="margin-right: 5px; display: flex; flex-direction: row">

                            <div>
                                {`${item.date}`}
                            </div>

                            <div id="editBtnDiv" role="button" tabindex="0" on:keydown={doNothing}
                                 on:click={() => clickItemHandler(item.incomeId)}>
                                <span id="editBtn"><svg
                                        xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><path
                                        d="M471.6 21.7c-21.9-21.9-57.3-21.9-79.2 0L362.3 51.7l97.9 97.9 30.1-30.1c21.9-21.9 21.9-57.3 0-79.2L471.6 21.7zm-299.2 220c-6.1 6.1-10.8 13.6-13.5 21.9l-29.6 88.8c-2.9 8.6-.6 18.1 5.8 24.6s15.9 8.7 24.6 5.8l88.8-29.6c8.2-2.7 15.7-7.4 21.9-13.5L437.7 172.3 339.7 74.3 172.4 241.7zM96 64C43 64 0 107 0 160V416c0 53 43 96 96 96H352c53 0 96-43 96-96V320c0-17.7-14.3-32-32-32s-32 14.3-32 32v96c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h96c17.7 0 32-14.3 32-32s-14.3-32-32-32H96z"/></svg></span>
                            </div>

                            <div id="deleteBtnDiv" role="button" tabindex="0" on:keydown={doNothing}
                                 on:click={() => clickDeleteHandler(item.incomeId)}>
                                <span id="deleteBtn"><svg
                                        xmlns="http://www.w3.org/2000/svg" height="16" width="14" viewBox="0 0 448 512"><path
                                        d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"/></svg></span>
                            </div>
                            </div>
                    </div>

                    {#if dropdownStates[item.incomeId]}
                        <EditEntry {item} bind:isOn={dropdownStates[item.incomeId]}/>
                    {/if}

                    {#if deleteDropdownStates[item.incomeId]}
                        <div style="padding: 5px; margin-top: 5px; display:flex; flex-direction: column; justify-content: space-evenly"
                             class="inputForm" transition:slide>
                            <span id="textf" style="text-align: center; margin-bottom: 10px">Confirm deletion?</span>
                            <div style="display:flex; flex-direction: row; justify-content: space-evenly">
                                <button id="confirmBtn">CONFIRM</button>
                                <button id="cancelBtn">CANCEL</button>
                            </div>

                            <!--                            <button style="background-color: #8BD17C" on:click={() => console.log("LOL")}>Delete</button>-->
                            <!--                            <button style="background-color: palevioletred" on:click={clickItemHandler(item.expenseId)}>Cancel</button>-->
                        </div>
                    {/if}

                </li>
            {/each}
        </ul>
    </div>
</div>

<style>

    #textf {
        font-family: "Inter UI", "SF Pro Display", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
        font-size: 20px;
        font-weight: 500;
    }

    #confirmBtn {
        background-image: linear-gradient(92.88deg, #455EB5 9.16%, #5643CC 43.89%, #673FD7 64.72%);
        border-radius: 8px;
        border-style: none;
        box-sizing: border-box;
        color: #FFFFFF;
        cursor: pointer;
        flex-shrink: 0;
        font-family: "Inter UI", "SF Pro Display", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
        font-size: 16px;
        font-weight: 500;
        height: 3rem;
        padding: 0 1.6rem;
        text-align: center;
        text-shadow: rgba(0, 0, 0, 0.25) 0 3px 8px;
        transition: all .5s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
    }

    #confirmBtn:hover {
        box-shadow: rgba(80, 63, 205, 0.5) 0 1px 30px;
        transition-duration: .1s;
    }

    #cancelBtn {
        background-image: linear-gradient(92.88deg, #455EB5 9.16%, #5643CC 43.89%, #673FD7 64.72%);
        border-radius: 8px;
        border-style: none;
        box-sizing: border-box;
        color: #FFFFFF;
        cursor: pointer;
        flex-shrink: 0;
        font-family: "Inter UI", "SF Pro Display", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
        font-size: 16px;
        font-weight: 500;
        height: 3rem;
        padding: 0 1.6rem;
        text-align: center;
        text-shadow: rgba(0, 0, 0, 0.25) 0 3px 8px;
        transition: all .5s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
    }

    #cancelBtn:hover {
        box-shadow: rgba(80, 63, 205, 0.5) 0 1px 30px;
        transition-duration: .1s;
    }

    @media only screen and (max-width: 900px) {
        #listContainer {
            max-height: 50vh;
            width: 100%;
        }

        #incomeInfo {
            margin: 0;
            width: 100%;
        }
    }

    #editBtn {
        margin-left: 10px;
        margin-right: 5px;
        fill: darkblue;
    }

    .inputForm {
        display: flex;
        flex-direction: column;
        margin-bottom: 10px;
    }

    #editBtn:hover {
        cursor: pointer;
        fill: lightseagreen;
    }

    #deleteBtn {
        fill: red;
        margin-left: 5px;
    }

    #deleteBtn:hover {
        cursor: pointer;
        fill: palevioletred;
    }

    #incomeInfo {
        min-width: 350px;
        min-height: 0;
        background-color: #212942;
        color: white;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }

    #listContainer {
        flex: 1 1 auto;
        overflow-y: auto;
        min-height: 0;
        padding: 0 10px 10px;
        /*margin: 0 0 10px;*/
        box-sizing: border-box;
        border-radius: 0 0 10px 10px;
    }

    ::-webkit-scrollbar {
        width: 10px;
    }

    ::-webkit-scrollbar-track {
        background: #f1f1f1;
        border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb {
        background: #888;
        border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb:hover {
        background: #555;
    }

    #listContainer ul {
        list-style: none;
        padding: 0;
        border-radius: 0 0 10px 10px;
    }

    #listContainer li {
        margin-bottom: 20px;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
    }

    #listContainer li:hover {
        box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    }

    .button {
        align-items: center;
        background-color: #0A66C2;
        border: 0;
        border-radius: 100px;
        box-sizing: border-box;
        color: #ffffff;
        cursor: pointer;
        display: inline-flex;
        font-family: -apple-system, system-ui, system-ui, "Segoe UI", Roboto, "Helvetica Neue", "Fira Sans", Ubuntu, Oxygen, "Oxygen Sans", Cantarell, "Droid Sans", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Lucida Grande", Helvetica, Arial, sans-serif;
        font-size: 16px;
        font-weight: 600;
        justify-content: center;
        line-height: 20px;
        max-width: 480px;
        min-height: 40px;
        min-width: 0;
        overflow: hidden;
        padding: 0 20px;
        text-align: center;
        touch-action: manipulation;
        transition: background-color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s, box-shadow 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s, color 0.167s cubic-bezier(0.4, 0, 0.2, 1) 0s;
        user-select: none;
        -webkit-user-select: none;
        vertical-align: middle;
    }

    .button:hover,
    .button:focus {
        background-color: #16437E;
        color: #ffffff;
    }

    .button:active {
        background: #09223b;
        color: rgb(255, 255, 255, .7);
    }

    .button:disabled {
        cursor: not-allowed;
        background: rgba(0, 0, 0, .08);
        color: rgba(0, 0, 0, .3);
    }

    #date-list {
        background-color: black;
        margin: 0 20px 20px;
        max-height: 400px;
        overflow-y: scroll;
        border-radius: 20px;
    }

    .date-entry {
        background-image: linear-gradient(92.88deg, #455EB5 9.16%, #5643CC 43.89%, #673FD7 64.72%);
        border-radius: 8px;
        border-style: none;
        box-sizing: border-box;
        color: #FFFFFF;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
        font-family: "Inter UI", "SF Pro Display", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
        font-size: 16px;
        font-weight: 500;
        height: 3rem;
        padding: 0 1.6rem;
        text-align: center;
        text-shadow: rgba(0, 0, 0, 0.25) 0 3px 8px;
        transition: all .3s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
        margin: 10px;
    }

    .date-entry:hover {
        box-shadow: rgba(255, 255, 255, 0.8) 0 0 20px;
    }
</style>
