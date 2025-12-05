package org.study.bootcamp.task_39.executor_of_tasks.v1.api.cli;

import org.study.bootcamp.task_39.executor_of_tasks.v1.application.service.WeasleyFamily;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        List<String> chores = Arrays.asList(
                "полить мандрагоры...",
                "почистить камин...",
                "подмести пол...",
                "покормить сов...",
                "приготовить завтрак...",
                "приготовить обед...",
                "приготовить ужин...",
                "помыть посуду...",
                "прибрать в гостиной..."
        );

        WeasleyFamily weasleyFamily = new WeasleyFamily(chores);

        weasleyFamily.performChores();
    }
}
