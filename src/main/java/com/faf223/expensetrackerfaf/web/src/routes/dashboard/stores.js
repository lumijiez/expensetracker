import {writable} from "svelte/store";

export const incomeData = writable([]);

export const expenseData = writable([]);

export const incomeTypes = writable([]);

export const expenseTypes = writable([]);

export const tempExpense = writable([])

export const tempIncome = writable([]);

export let selectedTab = writable('expenses');

export let dateText = writable("This Month");