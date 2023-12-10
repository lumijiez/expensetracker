import {writable} from "svelte/store";

export const incomeData = writable([]);

export const expenseData = writable([]);

export const incomeTypes = writable([]);

export const expenseTypes = writable([]);

export const tempExpense = writable([])

export const tempIncome = writable([]);

export const monthIncome = writable([]);

export const monthExpense = writable([]);

export const categorizedExpense = writable([]);

export const categorizedIncome = writable([]);

export let isCategorizedExpense = writable(false);

export let isCategorizedIncome = writable(false);

export let selectedTab = writable('expenses');

export let dateText = writable("This Month");