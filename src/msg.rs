use cosmwasm_schema::{cw_serde, QueryResponses};

#[cw_serde]
pub struct InstantiateMsg {
    pub count: i32,
    pub carowner: String,
}

#[cw_serde]
pub enum ExecuteMsg {
    Register {},
    Increment {},
    Reset { count: i32 },
}

#[cw_serde]
#[derive(QueryResponses)]
pub enum QueryMsg {
    // GetCount returns the current count as a json-encoded number
    #[returns(GetCountResponse)]
    GetCount {},
    #[returns(GetOwnerResponse)]
    GetOwner {},
}

// We define a custom struct for each query response
#[cw_serde]
pub struct GetCountResponse {
    pub count: i32,
}

#[cw_serde]
pub struct GetOwnerResponse {
    pub owner: String,
}
