<script>
    import ContentExpense from "./ContentExpense.svelte";
    import {dateText, expenseData, expenseTypes, incomeData, tempExpense, tempIncome} from "../../../stores.js";
    import { globalStyles } from "../../../styles.js";
    import {onMount} from "svelte";
    import axios from "axios";
    import {getCookie} from "svelte-cookie";
    import { slide } from 'svelte/transition'

    const textToIcon = {
        'Groceries': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5H24C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z\"/></svg>",
        'Utilities': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M176 88v40H336V88c0-4.4-3.6-8-8-8H184c-4.4 0-8 3.6-8 8zm-48 40V88c0-30.9 25.1-56 56-56H328c30.9 0 56 25.1 56 56v40h28.1c12.7 0 24.9 5.1 33.9 14.1l51.9 51.9c9 9 14.1 21.2 14.1 33.9V304H384V288c0-17.7-14.3-32-32-32s-32 14.3-32 32v16H192V288c0-17.7-14.3-32-32-32s-32 14.3-32 32v16H0V227.9c0-12.7 5.1-24.9 14.1-33.9l51.9-51.9c9-9 21.2-14.1 33.9-14.1H128zM0 416V336H128v16c0 17.7 14.3 32 32 32s32-14.3 32-32V336H320v16c0 17.7 14.3 32 32 32s32-14.3 32-32V336H512v80c0 35.3-28.7 64-64 64H64c-35.3 0-64-28.7-64-64z\"/></svg>",
        'Rent': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M575.8 255.5c0 18-15 32.1-32 32.1h-32l.7 160.2c0 2.7-.2 5.4-.5 8.1V472c0 22.1-17.9 40-40 40H456c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1H416 392c-22.1 0-40-17.9-40-40V448 384c0-17.7-14.3-32-32-32H256c-17.7 0-32 14.3-32 32v64 24c0 22.1-17.9 40-40 40H160 128.1c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2H104c-22.1 0-40-17.9-40-40V360c0-.9 0-1.9 .1-2.8V287.6H32c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z\"/></svg>",
        'Transportation': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M135.2 117.4L109.1 192H402.9l-26.1-74.6C372.3 104.6 360.2 96 346.6 96H165.4c-13.6 0-25.7 8.6-30.2 21.4zM39.6 196.8L74.8 96.3C88.3 57.8 124.6 32 165.4 32H346.6c40.8 0 77.1 25.8 90.6 64.3l35.2 100.5c23.2 9.6 39.6 32.5 39.6 59.2V400v48c0 17.7-14.3 32-32 32H448c-17.7 0-32-14.3-32-32V400H96v48c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V400 256c0-26.7 16.4-49.6 39.6-59.2zM128 288a32 32 0 1 0 -64 0 32 32 0 1 0 64 0zm288 32a32 32 0 1 0 0-64 32 32 0 1 0 0 64z\"/></svg>",
        'Education': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"20\" viewBox=\"0 0 640 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M337.8 5.4C327-1.8 313-1.8 302.2 5.4L166.3 96H48C21.5 96 0 117.5 0 144V464c0 26.5 21.5 48 48 48H256V416c0-35.3 28.7-64 64-64s64 28.7 64 64v96H592c26.5 0 48-21.5 48-48V144c0-26.5-21.5-48-48-48H473.7L337.8 5.4zM96 192h32c8.8 0 16 7.2 16 16v64c0 8.8-7.2 16-16 16H96c-8.8 0-16-7.2-16-16V208c0-8.8 7.2-16 16-16zm400 16c0-8.8 7.2-16 16-16h32c8.8 0 16 7.2 16 16v64c0 8.8-7.2 16-16 16H512c-8.8 0-16-7.2-16-16V208zM96 320h32c8.8 0 16 7.2 16 16v64c0 8.8-7.2 16-16 16H96c-8.8 0-16-7.2-16-16V336c0-8.8 7.2-16 16-16zm400 16c0-8.8 7.2-16 16-16h32c8.8 0 16 7.2 16 16v64c0 8.8-7.2 16-16 16H512c-8.8 0-16-7.2-16-16V336zM232 176a88 88 0 1 1 176 0 88 88 0 1 1 -176 0zm88-48c-8.8 0-16 7.2-16 16v32c0 8.8 7.2 16 16 16h32c8.8 0 16-7.2 16-16s-7.2-16-16-16H336V144c0-8.8-7.2-16-16-16z\"/></svg>",
        'Restaurants & Cafes': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M0 192c0-35.3 28.7-64 64-64c.5 0 1.1 0 1.6 0C73 91.5 105.3 64 144 64c15 0 29 4.1 40.9 11.2C198.2 49.6 225.1 32 256 32s57.8 17.6 71.1 43.2C339 68.1 353 64 368 64c38.7 0 71 27.5 78.4 64c.5 0 1.1 0 1.6 0c35.3 0 64 28.7 64 64c0 11.7-3.1 22.6-8.6 32H8.6C3.1 214.6 0 203.7 0 192zm0 91.4C0 268.3 12.3 256 27.4 256H484.6c15.1 0 27.4 12.3 27.4 27.4c0 70.5-44.4 130.7-106.7 154.1L403.5 452c-2 16-15.6 28-31.8 28H140.2c-16.1 0-29.8-12-31.8-28l-1.8-14.4C44.4 414.1 0 353.9 0 283.4z\"/></svg>",
        'Home Maintenance': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M575.8 255.5c0 18-15 32.1-32 32.1h-32l.7 160.2c0 2.7-.2 5.4-.5 8.1V472c0 22.1-17.9 40-40 40H456c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1H416 392c-22.1 0-40-17.9-40-40V448 384c0-17.7-14.3-32-32-32H256c-17.7 0-32 14.3-32 32v64 24c0 22.1-17.9 40-40 40H160 128.1c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2H104c-22.1 0-40-17.9-40-40V360c0-.9 0-1.9 .1-2.8V287.6H32c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z\"/></svg>",
        'Transport': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M135.2 117.4L109.1 192H402.9l-26.1-74.6C372.3 104.6 360.2 96 346.6 96H165.4c-13.6 0-25.7 8.6-30.2 21.4zM39.6 196.8L74.8 96.3C88.3 57.8 124.6 32 165.4 32H346.6c40.8 0 77.1 25.8 90.6 64.3l35.2 100.5c23.2 9.6 39.6 32.5 39.6 59.2V400v48c0 17.7-14.3 32-32 32H448c-17.7 0-32-14.3-32-32V400H96v48c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V400 256c0-26.7 16.4-49.6 39.6-59.2zM128 288a32 32 0 1 0 -64 0 32 32 0 1 0 64 0zm288 32a32 32 0 1 0 0-64 32 32 0 1 0 0 64z\"/></svg>",
        'Shopping': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5H24C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z\"/></svg>",
        'Miscellaneous': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M403.8 34.4c12-5 25.7-2.2 34.9 6.9l64 64c6 6 9.4 14.1 9.4 22.6s-3.4 16.6-9.4 22.6l-64 64c-9.2 9.2-22.9 11.9-34.9 6.9s-19.8-16.6-19.8-29.6V160H352c-10.1 0-19.6 4.7-25.6 12.8L284 229.3 244 176l31.2-41.6C293.3 110.2 321.8 96 352 96h32V64c0-12.9 7.8-24.6 19.8-29.6zM164 282.7L204 336l-31.2 41.6C154.7 401.8 126.2 416 96 416H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H96c10.1 0 19.6-4.7 25.6-12.8L164 282.7zm274.6 188c-9.2 9.2-22.9 11.9-34.9 6.9s-19.8-16.6-19.8-29.6V416H352c-30.2 0-58.7-14.2-76.8-38.4L121.6 172.8c-6-8.1-15.5-12.8-25.6-12.8H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H96c30.2 0 58.7 14.2 76.8 38.4L326.4 339.2c6 8.1 15.5 12.8 25.6 12.8h32V320c0-12.9 7.8-24.6 19.8-29.6s25.7-2.2 34.9 6.9l64 64c6 6 9.4 14.1 9.4 22.6s-3.4 16.6-9.4 22.6l-64 64z\"/></svg>",
        'Charity': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M163.9 136.9c-29.4-29.8-29.4-78.2 0-108s77-29.8 106.4 0l17.7 18 17.7-18c29.4-29.8 77-29.8 106.4 0s29.4 78.2 0 108L310.5 240.1c-6.2 6.3-14.3 9.4-22.5 9.4s-16.3-3.1-22.5-9.4L163.9 136.9zM568.2 336.3c13.1 17.8 9.3 42.8-8.5 55.9L433.1 485.5c-23.4 17.2-51.6 26.5-80.7 26.5H192 32c-17.7 0-32-14.3-32-32V416c0-17.7 14.3-32 32-32H68.8l44.9-36c22.7-18.2 50.9-28 80-28H272h16 64c17.7 0 32 14.3 32 32s-14.3 32-32 32H288 272c-8.8 0-16 7.2-16 16s7.2 16 16 16H392.6l119.7-88.2c17.8-13.1 42.8-9.3 55.9 8.5zM193.6 384l0 0-.9 0c.3 0 .6 0 .9 0z\"/></svg>",
        'Legal Services': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M318.6 9.4c-12.5-12.5-32.8-12.5-45.3 0l-120 120c-12.5 12.5-12.5 32.8 0 45.3l16 16c12.5 12.5 32.8 12.5 45.3 0l4-4L325.4 293.4l-4 4c-12.5 12.5-12.5 32.8 0 45.3l16 16c12.5 12.5 32.8 12.5 45.3 0l120-120c12.5-12.5 12.5-32.8 0-45.3l-16-16c-12.5-12.5-32.8-12.5-45.3 0l-4 4L330.6 74.6l4-4c12.5-12.5 12.5-32.8 0-45.3l-16-16zm-152 288c-12.5-12.5-32.8-12.5-45.3 0l-112 112c-12.5 12.5-12.5 32.8 0 45.3l48 48c12.5 12.5 32.8 12.5 45.3 0l112-112c12.5-12.5 12.5-32.8 0-45.3l-1.4-1.4L272 285.3 226.7 240 168 298.7l-1.4-1.4z\"/></svg>"
    };

    let isDateDropdownExpanded = false
    let isCategoryDropdownExpanded = false
    let dropdownStates = {};

    $: {
        dropdownStates = {};
        $expenseData.toReversed().forEach(data => {
            dropdownStates[data.expenseId] = false;
        });
    }
    function clickHandlerDate() {
        isDateDropdownExpanded = !isDateDropdownExpanded
    }

    function clickItemHandler(id) {
        dropdownStates[id] = !dropdownStates[id];
    }

    function clickHandlerCategory() {
        isCategoryDropdownExpanded = !isCategoryDropdownExpanded;
    }

    function clickOutsideHandler(event) {
        const isDateButton = event.target.closest("#btn1");
        const isCategoryButton = event.target.closest("#btn2");

        if (!isDateButton) {
            isDateDropdownExpanded = false;
        }

        if (!isCategoryButton) {
            isCategoryDropdownExpanded = false;
        }
    }

    onMount(() => {
        document.body.addEventListener("click", clickOutsideHandler);

        return () => {
            document.body.removeEventListener("click", clickOutsideHandler);
        };
    });

    async function getToday() {
        var currentDate = new Date();
        var currentDay = currentDate.toISOString().split('T')[0];
        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?date=' + currentDay, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?date=' + currentDay, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            $dateText = "Today"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getYesterday() {
        var currentDate = new Date();

        var yesterday = new Date(currentDate);
        yesterday.setDate(currentDate.getDate() - 1);

        var yesterdayString = yesterday.toISOString().split('T')[0];

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?date=' + yesterdayString, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?date=' + yesterdayString, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });
            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            $dateText = "Yesterday"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getMonth() {
        var currentDate = new Date();
        var year = currentDate.getMonth() + 1;

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            $dateText = "This Month"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getLastMonth() {
        var currentDate = new Date();
        var year = currentDate.getMonth();

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data)
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?month=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            $dateText = "Last Month"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    async function getLastYear() {
        var currentDate = new Date();
        var year = currentDate.getFullYear();

        try {
            const response1 = await axios.get('https://trackio.online:8081/expenses/personal-expenses?year=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            expenseData.set(response1.data);
            tempExpense.set(response1.data);
            const response2 = await axios.get('https://trackio.online:8081/incomes/personal-incomes?year=' + year, {
                headers: {
                    'Authorization': `Bearer ${getCookie('access_token')}`
                }
            });

            incomeData.set(response2.data);
            tempIncome.set(response2.data);
            $dateText = "This Year"
        } catch (error) {
            console.error("Error fetching expenses:", error);
        }
    }

    function filterByCategory(category) {
        let tempArr = $tempExpense.filter(expense => expense.expenseCategory.name === category);
        expenseData.set(tempArr);
    }

</script>

<div id="expenseInfo" style="background-color: {$globalStyles.mainColor}">
    <ContentExpense />
    <div style="display: flex; justify-content: space-between">
        <div id="dropdown-date" style="margin: 10px;">
            <button id="btn1" class="button" on:click={clickHandlerDate}>Filter by Date ▼</button>
            {#if isDateDropdownExpanded}
                <div id="date-list" transition:slide>
                    <div class="date-entry" on:click={() => getToday()}>Today</div>
                    <div class="date-entry" on:click={() => getYesterday()}>Yesterday</div>
                    <div class="date-entry" on:click={() => getMonth()}>This month</div>
                    <div class="date-entry" on:click={() => getLastMonth()}>Last month</div>
                    <div class="date-entry" on:click={() => getLastYear()}>This year</div>
                </div>
            {/if}
        </div>

        <div id="dropdown-category" style="margin: 10px;">
            <button id="btn2" class="button" on:click={clickHandlerCategory}>Filter by Category ▼</button>
            {#if isCategoryDropdownExpanded}
                <div id="date-list" transition:slide>
                    {#each $expenseTypes as expense (expense.id)}
                        {#if expense.id !== undefined}
                            <div class="date-entry" on:click={() => filterByCategory(expense.name)} value={expense.id}>{expense.name}</div>
                        {/if}
                    {/each}
                </div>
            {/if}
        </div>
    </div>
    <div id="listContainer" style="color: {$globalStyles.color}">
        <ul>
            {#each $expenseData.toReversed() as item (item.expenseId)}
                <li style="display:flex; justify-content: space-between; align-items: center; color: {$globalStyles.color}">
                    <span>
                        {#if textToIcon[item.expenseCategory.name]}
                        {@html textToIcon[item.expenseCategory.name]}
                    {/if}
                    <span style="font-weight: bold">{item.incomeCategory ? `${item.incomeCategory.name}: ` : `${item.expenseCategory.name}: `}</span>
                    <span style="font-weight:bold; margin-right: 10px; color: red; font-size: larger">{item.incomeCategory ? `+${item.amount}$` : `-${item.amount}$`}</span>
                    </span>
                    <span style="margin-right: 5px;">{`${item.date}`}
                        <span id="editBtn" on:click={clickItemHandler(item.expenseId)}><svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M471.6 21.7c-21.9-21.9-57.3-21.9-79.2 0L362.3 51.7l97.9 97.9 30.1-30.1c21.9-21.9 21.9-57.3 0-79.2L471.6 21.7zm-299.2 220c-6.1 6.1-10.8 13.6-13.5 21.9l-29.6 88.8c-2.9 8.6-.6 18.1 5.8 24.6s15.9 8.7 24.6 5.8l88.8-29.6c8.2-2.7 15.7-7.4 21.9-13.5L437.7 172.3 339.7 74.3 172.4 241.7zM96 64C43 64 0 107 0 160V416c0 53 43 96 96 96H352c53 0 96-43 96-96V320c0-17.7-14.3-32-32-32s-32 14.3-32 32v96c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h96c17.7 0 32-14.3 32-32s-14.3-32-32-32H96z"/></svg></span>
                        <span id="deleteBtn"><svg xmlns="http://www.w3.org/2000/svg" height="16" width="14" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"/></svg></span>
                    </span>

                </li>
                {#if dropdownStates[item.expenseId]}
                    <div class="inputForm" transition:slide>
                        <input type="text" id="input1" placeholder="{item.amount}">
                        <input type="text" id="input2" placeholder="{item.expenseCategory.name}">
                        <input type="text" id="input3" placeholder="{item.date}">
                        <div style="display:flex; justify-content: space-evenly">
                            <button style="background-color: #8BD17C" on:click={() => console.log("LOL")}>Submit</button>
                            <button style="background-color: palevioletred" on:click={clickItemHandler(item.expenseId)}>Cancel</button>
                        </div>

                    </div>
                {/if}
            {/each}
        </ul>
    </div>
</div>

<style>


    @media only screen and (max-width: 900px) {
        #listContainer {
            max-height: 50vh;
            width: 100%;
        }

        #expenseInfo {
            margin: 0;
            width: 100%;
        }
    }

    #editBtn {
        margin-left: 5px;
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
    }

    #deleteBtn:hover {
        cursor: pointer;
        fill: palevioletred;
    }

    #expenseInfo {
        min-width: 350px;
        min-height: 0;
        background-color: #212942;
        color: white;
        /*border-radius: 0 0 10px 10px;*/
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

    /* Track */
    ::-webkit-scrollbar-track {
        background: #f1f1f1;
        border-radius: 10px;
    }

    /* Handle */
    ::-webkit-scrollbar-thumb {
        background: #888;
        border-radius: 10px;
    }

    /* Handle on hover */
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
        min-width: 0px;
        overflow: hidden;
        padding: 0px;
        padding-left: 20px;
        padding-right: 20px;
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
        background-color: #007BFF;
        position:absolute;
        margin-top: 20px;
        max-height: 400px;
        overflow-y: scroll;
        border-radius: 20px;
        z-index:1;
    }

    .date-entry {
        padding: 10px;
        margin: 10px;
        background-color: black;
        color: white;
        border-radius: 20px;
        cursor: pointer;
    }

    .date-entry:hover {
        background-color: rgb(128, 128, 128);
    }
</style>
