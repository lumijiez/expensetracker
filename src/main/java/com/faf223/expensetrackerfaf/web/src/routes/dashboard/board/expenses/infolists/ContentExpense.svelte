<script>
    import Modal from './Modal.svelte';
    import { writable } from 'svelte/store';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";
    import {expenseTypes, expenseData} from "../../../stores.js";


    let showModal;
    let amount = '';
    let newData;

    const selectedExpenseId = writable('');

    function addNewExpense(id, amount) {
        const today = new Date().toISOString().split('T')[0];
        const expenseCategory = $expenseTypes.find(incomeType => incomeType.id === id);

        if (expenseCategory) {
            const newIncome = {
                incomeId: 0,
                userDTO: {
                    name: "Dummy",
                    surname: "User",
                    username: "dummyuser"
                },
                expenseCategory: expenseCategory,
                date: today,
                amount: parseInt(amount)
            };

            newData = $expenseData;
            newData.push(newIncome);
            $expenseData = newData;
        } else {
            console.error('Expense category not found for id:', id);
        }
    }

    const createExpense = async () => {
        const selectedExpense = $expenseTypes.find(expense => expense.id === $selectedExpenseId);
        const data = {
            expenseCategory: selectedExpense.id,
            amount: parseInt(amount),
        };

        addNewExpense(selectedExpense.id, parseInt(amount));

        try {
            const token = getCookie('access_token');

            const response = await axios.post('http://localhost:8081/expenses', data, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 201) {
                //console.log("cool");
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
                    {#each $expenseTypes as expense (expense.id)}
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
        padding: 10px 20px;
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
