freeStyleJob('allure') {

    publishers {
        allure('target/allure-results') {
            buildFor('UNSTABLE')
            reportVersion('1.4.16')
            includeProperties(true)
        }
    }
}