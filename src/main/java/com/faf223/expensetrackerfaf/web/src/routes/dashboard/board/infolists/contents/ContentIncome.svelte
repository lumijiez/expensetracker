<script>
    import Modal from '../modals/Modal.svelte';
    import { onMount } from 'svelte';
    import { writable } from 'svelte/store';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";
    import {incomeData} from "../../../stores.js";
    import {incomeTypes} from "../../../stores.js";

    let showModal;
    let amount = '';
    let newData;

    const selectedIncomeId = writable('');

    function addNewIncome(id, amount) {
        const today = new Date().toISOString().split('T')[0];
        const incomeCategory = $incomeTypes.find(incomeType => incomeType.id === id);

        console.log(amount);

        if (incomeCategory) {
            const newIncome = {
                incomeId: 0,
                userDTO: {
                    name: "Dummy",
                    surname: "User",
                    username: "dummyuser"
                },
                incomeCategory: incomeCategory,
                date: today,
                amount: amount
            };

            newData = $incomeData;
            newData.push(newIncome);
            $incomeData = newData;
            console.log("ggWPPPPP", newIncome);
        } else {
            console.error('Income category not found for id:', id);
        }
    }


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
            amount: amount,
        };

        addNewIncome(selectedIncome.id, amount);

        try {
            const token = getCookie('access_token');
            console.log(token);
            const response = await axios.post('http://localhost:8081/incomes', data, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

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
    <div id="optionField">
        <h2>Incomes</h2>
        <div id="openModal" class="plus-button" role="button" tabindex="1" on:click={() => (showModal = true)} on:keydown={() => console.log("keydown")}>
            +
        </div>
    </div>
    <Modal bind:showModal>
        <div class="income-form">
            <h3>Income Details</h3>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="text" id="amount" class="form-control" bind:value={amount} />
            </div>

            <div class="form-group">
                <label for="incomeCategory">Select Income Category:</label>
                <select id="incomeCategory" class="form-control" bind:value={$selectedIncomeId}>
                    {#each $incomeOptions as income (income.id)}
                        {#if income.id !== undefined}
                            <option value={income.id}>{income.name}</option>
                        {/if}
                    {/each}
                </select>
            </div>

            <button class="btn btn-primary" on:click={createIncome}>Submit</button>
        </div>
    </Modal>
</div>


<style>
    #inc {
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

    .income-form {
        background-color: #fff;
        border-radius: 5px;
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
