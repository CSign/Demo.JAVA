package se.devcode.csign.demo.client.model;

import java.util.Arrays;

public enum ScenarioCode {
    ANY_INDIVIDUAL(1000),
    SPECIFIC_INDIVIDUAL(1001),
    SIGNATORY_OR_POWER_OF_ATTORNEY_HOLDER(2000),
    ANY_ORGANIZATION_REPRESENTATIVE(2001),
    ANY_ORGANIZATION_REPRESENTATIVE_NON_SWEDISH(2002);

    private int code;

    ScenarioCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ScenarioCode fromCode(int code) {
        return Arrays.stream(ScenarioCode.values())
                .filter(scenarioCode -> scenarioCode.getCode() == code)
                .findFirst()
                .map(scenarioCode -> ScenarioCode.valueOf(scenarioCode.toString()))
                .orElse(null);
    }
}
