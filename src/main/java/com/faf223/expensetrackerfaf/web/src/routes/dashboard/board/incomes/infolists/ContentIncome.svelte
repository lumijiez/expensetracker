<script>
    import { writable } from 'svelte/store';
    import axios from 'axios';
    import { getCookie } from "svelte-cookie";
    import {incomeTypes, incomeData, dateText} from "../../../stores.js";
    import { slide } from 'svelte/transition';

    let showModal = false;
    let amount = '';
    let newData;

    const selectedIncomeId = writable('');

    function addNewIncome(expid, id, amount) {
        const today = new Date().toISOString().split('T')[0];
        const incomeCategory = $incomeTypes.find(incomeType => incomeType.id === id);

        if (incomeCategory) {
            const newIncome = {
                incomeId: expid,
                incomeCategory: incomeCategory,
                date: today,
                amount: parseInt(amount)
            };

            newData = $incomeData;
            newData.push(newIncome);
            $incomeData = newData;
        } else {
            console.error('Expense category not found for id:', id);
        }
    }

    const createIncome = async () => {
        showModal = false;
        const selectedIncome = $incomeTypes.find(income => income.id === $selectedIncomeId);
        const data = {
            incomeCategory: selectedIncome.id,
            amount: parseInt(amount),
        };

        try {
            const token = getCookie('access_token');

            const response = await axios.post('https://trackio.online:8081/incomes', data, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 201) {
                addNewIncome(response.data.incomeId, selectedIncome.id, parseInt(amount));
            } else {
                console.error('Error:', response.status);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    function toggleModal() {
        showModal = !showModal;
    }
</script>

<div id="exp">
    <div id="optionField">
        <h2>Incomes: {$dateText}</h2>
        <div id="openModal" class="plus-button" role="button" tabindex="0" on:click={toggleModal} on:keydown={() => console.log("keydown")}>
            +
        </div>
    </div>
        {#if showModal}
        <div class="income-form" transition:slide>
            <h3>Income Details</h3>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="text" id="amount" class="form-control" bind:value={amount} />
            </div>

            <div class="form-group">
                <label for="incomeCategory">Select Income Category:</label>
                <select id="incomeCategory" class="form-control" bind:value={$selectedIncomeId}>
                    {#each $incomeTypes as income (income.id)}
                        {#if income.id !== undefined}
                            <option value={income.id}>{income.name}</option>
                        {/if}
                    {/each}
                </select>
            </div>

            <div style="display: flex; justify-content: space-around">
                <button class="btn btn-primary" on:click={createIncome}>SUBMIT</button>
                <button class="btn btn-primary" on:click={() => showModal = false}>CANCEL</button>
            </div>

        </div>
    {/if}
</div>


<style>
    #exp {
        padding: 10px 20px;
        text-align: center;
    }

    button {
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

    button:hover {
        box-shadow: rgba(80, 63, 205, 0.5) 0 1px 30px;
        transition-duration: .1s;
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
        border-radius: 20px;
        padding: 20px;
        max-width: 400px;
        color: black;
    }

    input[type=text] {
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    select {
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        font-size: 16px;
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
