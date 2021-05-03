# Cowin Cli

> Queries Cowin API at intervals to check Covid vaccine availability

## Setup

You need [sbt](https://www.scala-sbt.org/) to run this script.

Currently the script runs for *minimum age 18* and *Gurgaon district*. But you can change these values: [District Id](https://github.com/rajatsharma/cowin-cli/blob/649b325d2ef353ce9ae921d9236fcdb76eac8f57/src/main/scala/Main.scala#L7) and [Minimum Age](https://github.com/rajatsharma/cowin-cli/blob/649b325d2ef353ce9ae921d9236fcdb76eac8f57/src/main/scala/Main.scala#L8) as per your desired values. 

You can get your District Id using [this](https://cdn-api.co-vin.in/api/v2/admin/location/states) to get your state and finally using this to get desired district id:

```js
`https://cdn-api.co-vin.in/api/v2/admin/location/districts/${state_id}`
```

## Usage

```shell
sbt run
```

## License

[![MIT](https://img.shields.io/badge/-MIT-black?style=flat-square)](/LICENSE)