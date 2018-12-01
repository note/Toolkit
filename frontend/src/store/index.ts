import Vue from 'vue';
import Vuex, {StoreOptions} from 'vuex';
import tools from './modules/tools';
import jobs from './modules/jobs';
import {RootState} from './types';
import localStoragePlugin from './plugins/localStoragePlugin';
import {devMode} from '@/main';

Vue.use(Vuex);

const store: StoreOptions<RootState> = {
    strict: process.env.NODE_ENV !== 'production',
    state: {
        loading: {
            tools: false,
            toolParameters: false,
        },
        maintenanceMode: false,
        reconnecting: false,
    },
    mutations: {
        startLoading(state, loadingType: string) {
            state.loading[loadingType] = true;
        },
        stopLoading(state, loadingType: string) {
            state.loading[loadingType] = false;
        },
    },
    modules: {
        tools,
        jobs,
    },
};

if (!devMode) {
    store.plugins = [localStoragePlugin];
}

export default new Vuex.Store<RootState>(store);