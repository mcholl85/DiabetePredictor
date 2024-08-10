import {useQuery} from "@tanstack/react-query"
import {fetchRiskByPatId} from "@/api/risk";

const useRisk = (patId: number) => {
    return useQuery({
        queryKey: ['risk', patId],
        queryFn: () => fetchRiskByPatId(patId),
        retry: 0
    })
}

export {useRisk}