<script>
    import { onMount, afterUpdate } from 'svelte';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";
    import ContentExpense from "./contents/ContentExpense.svelte";

    let data = [];
    let parentHeight;
    let listParentHeight;

    async function updateInfo() {
        const token = getCookie('access_token');

        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        try {
            const response = await axios.get('http://localhost:8081/expenses/personal-expenses', config);
            data = response.data;
            parentHeight = document.querySelector('#expenseInfo').offsetHeight;
        } catch (error) {
            console.error('Error:', error);
        }
    }
    onMount(updateInfo);

    afterUpdate(() => {
        parentHeight = document.querySelector('#expenseInfo').offsetHeight;
        listParentHeight = document.querySelector('#expenseList').offsetHeight;
    });
</script>

<div id="expenseInfo" style="max-height: {parentHeight}px;">
    <ContentExpense />

    <div id="expenseList" style="max-height: {listParentHeight}px;">
        <ul>
            {#each data as item}
                <li>
                    {item.incomeCategory ? `${item.incomeCategory.name}: ` : `${item.expenseCategory.name}: `}
                    {item.incomeCategory ? `+${item.amount}$` : `-${item.amount}$`}
                    {`${item.date}`}
                </li>
            {/each}
        </ul>
    </div>
</div>


<style>
    #expenseInfo {
        display: flex;
        flex-direction: column;
    }

    #expenseList {
        scrollbar-width: none;
        flex: 1;
        border-radius: 10px;
        margin: 10px;
        overflow-y: auto;
        max-height: 100%;
    }

    #expenseList::-webkit-scrollbar {
        display: none;
    }

    ul {
        list-style: none;
        padding: 0;
    }

    li {
        margin-bottom: 20px;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
    }

    li:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }
</style>
