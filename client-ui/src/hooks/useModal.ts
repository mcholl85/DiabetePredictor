import { Patient } from '@/types/patients';
import {useEffect, useState} from 'react';

const useModal = (patient?: Patient) => {
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
        if(patient) {
            setIsOpen(true);
        } else {
            setIsOpen(false);
        }
    }, [patient]);

    const openModal = () => setIsOpen(true);
    const closeModal = () => setIsOpen(false);

    return {
        isOpen,
        openModal,
        closeModal,
    };
};

export default useModal;