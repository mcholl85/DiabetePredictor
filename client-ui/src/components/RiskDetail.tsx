import {useRisk} from "@/hooks/useRisk";
import React from "react";
import {Badge, Card} from "flowbite-react";
import {RiskLevel} from "@/types/risk";
import {riskLevel} from "@/constants/riskLevel";


interface RiskDetailProps {
    patId: number
}

const RiskDetail: React.FC<RiskDetailProps> = ({patId}) => {
    const {data, isLoading, error} = useRisk(patId);
    const badgeColor = getBadgeColor(data?.level);

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (<Card className="max-w-2xl mx-auto">
        <div className="flex justify-between items-center">
            <div className="font-bold text-xl text-cyan-800">Rapport de diabète</div>
            <Badge color={badgeColor} size="sm">
                {data?.level && riskLevel[data.level]}
            </Badge>
        </div>
    </Card>)
}

const getBadgeColor = (level?: RiskLevel): string => {
    switch (level) {
        case 'NONE':
            return 'success';
        case 'BORDERLINE':
            return 'warning';
        case 'IN_DANGER':
            return 'failure';
        case 'EARLY_ONSET':
            return 'failure';
        default:
            return 'info';
    }
};

export default RiskDetail;