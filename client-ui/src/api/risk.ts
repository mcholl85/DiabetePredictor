import {Risk} from "@/types/risk"
import {API_URL} from "@/constants/apiUrl";

const fetchRiskByPatId = async (id: number): Promise<Risk> => {
    const response = await fetch(`${API_URL}/patients/${id}/risk`);

    if(!response.ok) throw new Error('Risk not found');

    return response.json();
}

export {fetchRiskByPatId}