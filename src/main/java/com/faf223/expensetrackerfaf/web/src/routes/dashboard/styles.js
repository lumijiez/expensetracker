import { writable } from 'svelte/store';

export const themeDefault = {
    mainColor: '#172233',
    dashColor: '#F5F2F3',
    color: 'black',
    altColor: 'white'
}

export const themeDark = {
    mainColor: '#000000',
    dashColor: '#202020',
    color: 'black',
    altColor: 'white'
}

export const globalStyles = writable(themeDefault);

