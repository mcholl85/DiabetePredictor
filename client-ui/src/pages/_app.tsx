'use client'
import {AppProps} from 'next/app';
import {HydrationBoundary, QueryClient, QueryClientProvider} from '@tanstack/react-query';
import {useState} from 'react';
import '../styles/globals.css';

function MyApp({Component, pageProps}: AppProps) {
    const [queryClient] = useState(() => new QueryClient());

    return (
            <QueryClientProvider client={queryClient}>
                <HydrationBoundary state={pageProps.dehydratedState}>
                    <Component {...pageProps} />
                </HydrationBoundary>
            </QueryClientProvider>
    );
}

export default MyApp;