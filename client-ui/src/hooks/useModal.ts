import { Patient } from '@/types/patients';
import {Dispatch, SetStateAction, useEffect, useState} from 'react';

interface useModalProps {
    patient?: Patient;
    setPatient: Dispatch<SetStateAction<Patient | undefined>>;
}

const useModal = ({patient, setPatient}: useModalProps) => {
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
        if(patient) {
            setIsOpen(true);
        } else {
            setIsOpen(false);
        }
    }, [patient]);

    const openModal = () => setIsOpen(true);
    const closeModal = () => {
        setIsOpen(false);
        setPatient(undefined)
    }

    return {
        isOpen,
        openModal,
        closeModal,
    };
};

export default useModal;