'use client'
import React from 'react';
import {Button, Label, Select, TextInput} from 'flowbite-react';
import usePatientForm from '@/hooks/usePatientForm';
import {Patient} from "@/types/patients";

interface PatientFormProps {
    patient?: Patient
    readOnly: boolean
    setReadOnly?: (value: boolean) => void
    classNames?: string
}

const PatientForm: React.FC<PatientFormProps> = ({patient, readOnly, setReadOnly, classNames}) => {
    const {register, handleSubmit, onSubmit, errors} = usePatientForm(patient);

    const handleFormSubmit = async (data: any) => {
        try {
            await onSubmit(data);
            if (setReadOnly)
                setReadOnly(true);
        } catch (error) {
            console.error("Form submission error:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
            <div className={classNames}>
                <div>
                    <Label htmlFor="firstName" className="ml-1">Prénom</Label>
                    <TextInput
                        id="firstName"
                        {...register('firstName', {required: 'First name is required'})}
                        color={errors.firstName ? 'failure' : (readOnly ? 'gray' : 'info')}
                        disabled={readOnly}
                    />
                    {errors.firstName && <p className="text-red-600">{errors.firstName.message}</p>}
                </div>

                <div>
                    <Label htmlFor="lastName" className="ml-1">Nom</Label>
                    <TextInput
                        id="lastName"
                        {...register('lastName', {required: 'Last name is required'})}
                        color={errors.lastName ? 'failure' : (readOnly ? 'gray' : 'info')}
                        disabled={readOnly}
                    />
                    {errors.lastName && <p className="text-red-600">{errors.lastName.message}</p>}
                </div>

                <div>
                    <Label htmlFor="birthDate" className="ml-1">Date de naissance</Label>
                    <TextInput
                        id="birthDate"
                        type="date"
                        {...register('birthDate', {required: 'Birth date is required'})}
                        color={errors.birthDate ? 'failure' : (readOnly ? 'gray' : 'info')}
                        disabled={readOnly}
                    />
                    {errors.birthDate && <p className="text-red-600">{errors.birthDate.message}</p>}
                </div>

                <div>
                    <Label htmlFor="gender" className="ml-1">Sexe</Label>
                    <Select
                        id="gender"
                        {...register('gender', {required: 'Gender is required'})}
                        color={errors.gender ? 'failure' : (readOnly ? 'gray' : 'info')}
                        disabled={readOnly}
                    >
                        <option value="">Select</option>
                        <option value="M">Homme</option>
                        <option value="F">Femme</option>
                    </Select>
                    {errors.gender && <p className="text-red-600">{errors.gender.message}</p>}
                </div>

                <div>
                    <Label htmlFor="address" className="ml-1">Adresse</Label>
                    <TextInput id="address" {...register('address')}
                               disabled={readOnly}
                               color={readOnly ? 'gray' : 'info'}
                    />
                </div>

                <div>
                    <Label htmlFor="phone" className="ml-1">Téléphone</Label>
                    <TextInput id="phone" {...register('phone')}
                               disabled={readOnly}
                               color={readOnly ? 'gray' : 'info'}
                    />
                </div>
            </div>
            {!readOnly && <Button className="bg-cyan-800 text-white" type="submit">Valider</Button>}
        </form>
    );
};
export default PatientForm;