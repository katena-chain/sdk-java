# SDK Java

## Requirements

- Maven >= 3

## Install Katena-chain Java SDK from Maven Central Repository

```xml
<dependecy>
    <groupId>com.github.katena-chain</groupId>
    <artifactId>sdk-java</artifactId>
    <version>1.1</version>
</dependecy>
```

## Usage

To rapidly interact with our API, you can use our `Transactor` helper. It handles all the steps needed to correctly
format, sign and send a tx.

Feel free to explore and modify its code to meet your expectations.

## Examples

Detailed examples are provided in the `examples` folder to explain how to use our `Transactor` helper methods.

Available examples:
* Send a `Certificate`
* Retrieve a `Certificate`
* Retrieve a list of historical `Certificate`
* Encrypt and send a `Secret`
* Retrieve a list of `Secret`

## Katena documentation

For more information, check the [katena documentation](https://doc.katena.transchain.io).