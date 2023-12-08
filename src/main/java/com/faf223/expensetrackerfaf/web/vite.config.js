import { sveltekit } from '@sveltejs/kit/vite';
import fs from 'fs'
import { defineConfig } from 'vite';


export default defineConfig({
	plugins: [sveltekit()],
	server: {
		port: 443,
		https: {
			key: fs.readFileSync('D:\\Source\\JavaProjects\\ExpenseTrackerFAF\\src\\main\\java\\com\\faf223\\expensetrackerfaf\\web\\privkey.pem'),
			cert: fs.readFileSync('D:\\Source\\JavaProjects\\ExpenseTrackerFAF\\src\\main\\java\\com\\faf223\\expensetrackerfaf\\web\\fullchain.pem'),
			proxy: {}
		}
	},
});
