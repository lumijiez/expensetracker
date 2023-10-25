<script>
    import Content from './Content.svelte';
    import Modal from 'svelte-simple-modal';

    import { onMount, afterUpdate } from 'svelte';
    import axios from 'axios';
    import {getCookie} from "svelte-cookie";

    let data = [];
    let parentHeight;

    onMount(async () => {
        const token = getCookie('access_token');

        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        try {
            const response = await axios.get('http://localhost:8081/incomes/personal-incomes', config);
            data = response.data;
            parentHeight = document.querySelector('#incomeInfo').offsetHeight;
        } catch (error) {
            console.error('Error:', error);
        }
    });

    afterUpdate(() => {
        parentHeight = document.querySelector('#incomeInfo').offsetHeight;
    });
</script>

<div id="incomeInfo" style="max-height: {parentHeight}px;">
    <div id="incomeOptions">
        <h2>Incomes</h2>
        <Modal><Content /></Modal>
    </div>


    <div id="incomeList">
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
    #incomeOptions {

        top: 0;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 10px 10px 0 0;
        z-index: 1;
        padding: 0 0 10px;
        margin: 0;
    }

    #incomeInfo {
        flex: 1;
        border-radius: 10px;
        margin: 10px;
        overflow-y: auto;
        max-height: 100%;
    }

    #incomeList {
        margin-top: 10px;
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
