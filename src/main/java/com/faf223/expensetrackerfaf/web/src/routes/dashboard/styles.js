import { writable } from 'svelte/store';

export const themeDefault = {
    mainColor: '#172233',
    dashColor: '#F5F2F3',
    color: 'black',
    altColor: 'white',
    dashTextColor: 'black',
    mainDataColor: '#F5F2F3'
}

export const themeDark = {
    mainColor: '#000000',
    dashColor: '#202020',
    color: 'black',
    altColor: 'white',
    dashTextColor: 'white',
    mainDataColor: 'black'
}

export const themeColorful = {
    mainColor: '#F75590',
    dashColor: '#FCE4D8',
    color: 'black',
    altColor: 'white',
    dashTextColor: 'black',
    mainDataColor: '#FCE4D8'
}
export const globalStyles = writable(themeDefault);

