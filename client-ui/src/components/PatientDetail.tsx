import {Card} from 'flowbite-react';
import {Patient} from '@/types/patients';
import PatientForm from "@/components/PatientForm";
import React, {useState} from "react";

interface PatientDetailProps {
    patient: Patient;
}

const PatientDetail: React.FC<PatientDetailProps> = ({patient}) => {
    const [readOnly, setReadOnly] = useState(true);
    return (
        <Card className="max-w-2xl mx-auto mt-1">
            <div className="flex justify-between items-center">
                <div className="font-bold text-xl text-cyan-800">Informations</div>
                <button className="text-cyan-800 hover:bg-gray-200 hover:text-black rounded-lg p-1.5"
                        type="button"
                        onClick={() => setReadOnly(!readOnly)}
                >
                    <svg className="h-6 w-6"
                        viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor"
                         fill="none" strokeLinecap="round" strokeLinejoin="round"
                    >
                        <path stroke="none" d="M0 0h24v24H0z"/>
                        <path d="M9 7 h-3a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-3"/>
                        <path d="M9 15h3l8.5 -8.5a1.5 1.5 0 0 0 -3 -3l-8.5 8.5v3"/>
                        <line x1="16" y1="5" x2="19" y2="8"/>
                    </svg>
                </button>
            </div>
            <PatientForm patient={patient} readOnly={readOnly} setReadOnly={setReadOnly}
                         classNames={"grid grid-cols-2 gap-3"}/>
        </Card>
    );
};

export default PatientDetail;