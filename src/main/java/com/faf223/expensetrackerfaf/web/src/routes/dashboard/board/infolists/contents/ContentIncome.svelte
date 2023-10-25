<script>
    import Modal from '../modals/Modal.svelte';
    import { onMount } from 'svelte';
    import { writable } from 'svelte/store';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";

    let showModal;
    let amount;

    const selectedIncomeId = writable('');

    onMount(async () => {
        try {
            const token = getCookie('access_token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            };

            const response = await axios.get('http://localhost:8081/incomes/categories', config);
            incomeOptions.set(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    });

    const incomeOptions = writable([]);

    const createIncome = async () => {
        const selectedIncome = $incomeOptions.find(income => income.id === $selectedIncomeId);
        const data = {
            incomeCategory: selectedIncome.id,
            amount: $amount,
        };

        try {
            const token = getCookie('access_token');
            console.log(token);
            const response = await axios.post('http://localhost:8081/incomes', data, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            console.log(response.data);

            if (response.status === 200) {
                console.log("cool");
            } else {
                console.error('Error:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };
</script>

<div id="inc">
    <h2>Incomes</h2>
    <div id="openModal" class="plus-button" role="button" tabindex="0" on:click={() => (showModal = true)} on:keydown={() => console.log("keydown")}>
        +
    </div>
    <Modal bind:showModal>
        <div>
            <label for="amount">Amount:</label>
            <input type="text" id="amount" bind:value={amount} />

            <label for="income">Select Income:</label>
            <select id="income" bind:value={$selectedIncomeId}>
                {#each $incomeOptions as income (income.id)}
                    {#if income.id !== undefined}
                        <option value={income.id}>{income.name}</option>
                    {/if}
                {/each}
            </select>

            <button on:click={createIncome}>Submit</button>
        </div>
    </Modal>
</div>

<style>
    #inc {
        padding-left: 20px;
        padding-right: 20px;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .plus-button {
        font-size: xx-large;
        background: none;
        cursor: pointer;
        border-radius: 10px;
        transition: background 0.3s ease;
    }

    .plus-button:hover {
        background: rgba(128, 128, 128, 0.5);
    }

    #openModal {
        top: 0;
        background-color: #f2f2f2;
        border-radius: 10px 10px 0 0;
        z-index: 1;
        margin: 0;
    }
</style>
