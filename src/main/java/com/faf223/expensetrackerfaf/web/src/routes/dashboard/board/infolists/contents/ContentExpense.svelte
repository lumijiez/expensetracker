<script>
    import Modal from '../modals/Modal.svelte';
    import { onMount } from 'svelte';
    import { writable } from 'svelte/store';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";

    let showModal;
    let amount = '';

    const selectedExpenseId = writable('');

    onMount(async () => {
        try {
            const token = getCookie('access_token');

            const config = {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            };

            const response = await axios.get('http://localhost:8081/expenses/categories', config);
            expenseOptions.set(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    });

    const expenseOptions = writable([]);

    const createExpense = async () => {
        const selectedExpense = $expenseOptions.find(expense => expense.id === $selectedExpenseId);
        const data = {
            expenseCategory: selectedExpense.id,
            amount: amount,
        };

        try {
            const token = getCookie('access_token');
            console.log(token);
            const response = await axios.post('http://localhost:8081/expenses', data, {
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

<div id="exp">
    <div id="optionField">
        <h2>Expenses</h2>
        <div id="openModal" class="plus-button" role="button" tabindex="0" on:click={() => (showModal = true)} on:keydown={() => console.log("keydown")}>
            +
        </div>
    </div>
    <Modal bind:showModal>
        <div class="expense-form">
            <h3>Expense Details</h3>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="text" id="amount" class="form-control" bind:value={amount} />
            </div>

            <div class="form-group">
                <label for="expenseCategory">Select Expense Category:</label>
                <select id="expenseCategory" class="form-control" bind:value={$selectedExpenseId}>
                    {#each $expenseOptions as expense (expense.id)}
                        {#if expense.id !== undefined}
                            <option value={expense.id}>{expense.name}</option>
                        {/if}
                    {/each}
                </select>
            </div>

            <button class="btn btn-primary" on:click={createExpense}>Submit</button>
        </div>
    </Modal>
</div>


<style>
    #exp {
        padding: 20px;
        text-align: center;
    }

    #optionField {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .plus-button {
        font-size: 24px;
        background-color: #007BFF;
        color: #fff;
        border: none;
        border-radius: 50%;
        width: 40px;
        height: 40px;
        line-height: 40px;
        cursor: pointer;
    }

    .plus-button:hover {
        background-color: #0056b3;
    }

    .expense-form {
        background-color: #fff;
        border-radius: 20px;
        padding: 20px;
        max-width: 400px;
        margin: 0 auto;
    }

    h3 {
        font-size: 20px;
        margin-bottom: 20px;
    }

    .form-group {
        margin-bottom: 15px;
    }

    label {
        display: block;
        font-weight: bold;
        margin-bottom: 5px;
    }

    .form-control {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    select.form-control {
        height: 40px;
    }

    .btn {
        background-color: #007BFF;
        color: #fff;
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        cursor: pointer;
    }

    .btn:hover {
        background-color: #0056b3;
    }
</style>
